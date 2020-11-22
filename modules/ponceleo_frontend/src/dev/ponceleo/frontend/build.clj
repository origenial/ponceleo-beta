(ns ponceleo.frontend.build
 "This namespace is reserved for build or code generation utilities.
  It is thought to get invokable from an REPL session or by clojurish scripts"
 (:require
   [clojure.java.io :as io]
   [clojure.string :as s :refer [join]]
   [me.raynes.fs :refer [mkdirs copy-dir delete-dir]]
   [ponceleo.frontend.spa.loading :refer [loading-page]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; SYSTEM UTILITIES

(defn copy-dir!
 "Utility function to copy a dir within another dir after cleaning the target
  dir"
 {:shadow.build/stage :configure}
 [source-dir within-to-dir]
 (let [source       (s/join "/" source-dir)
       dest         (s/join "/" within-to-dir)
       existing-dir (s/join "/" (concat within-to-dir [(last source-dir)]))]
  (mkdirs dest)             ;; Create directory if it doesn't exist
  (delete-dir existing-dir) ;; Delete target dir if it exists
  (copy-dir source dest)    ;; Copy directory
  (println "Copied '" source "' folder to '" dest "'.")))

;; END OF SYSTEM UTILIES
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; BUILD STRING CONTENT

(defn build-rel-path
 "Build a '/' separated relative path
  given a directory (string or collection of folder names) and a filename idk"
 [dir-name & [file]]
 (let [rel-dir (cond (string? dir-name) dir-name
                     (coll? dir-name) (s/join "/" dir-name))
       dir-file-sep (when-not (= (last rel-dir) \/) \/)]
  (str rel-dir dir-file-sep file)))

(defn build-css-import
 "Build a Javascript 'import CSS' statement (ES6 syntax)"
 [dir-name file-names]
 (s/join (for [f file-names]
          (str "import \"" (build-rel-path dir-name f) "\";\n"))))

(defn build-js-imports
 "Build a Javascript 'import {vars,...} from ' statement (ES6 syntax)
 Takes a #:next{:cljs-name :cljs-ns :cljs-dir} map as input"
 ([{:next/keys [cljs-name cljs-ns cljs-dir]}]
  (build-js-imports cljs-name cljs-dir cljs-ns))
 ([var-name file-pah]
  (str "import { " var-name " } from \"" file-pah "\";\n"))
 ([var-name cljs-dir var-ns]
  (let [file-path (build-rel-path cljs-dir (str var-ns ".js"))]
   (build-js-imports var-name file-path))))

(defn build-js-exports
 "Build a Javascript 'export const ... = ...' statement per argument given
 (ES6 syntax)"
 ([& exports]
  (let [aliased-exports
        (for [exp exports :let [{:next/keys [cljs-name export-as]} exp]]
         (if (= export-as "default")
          (str "export default " cljs-name ";")
          (str "export const " export-as " = " cljs-name ";")))]
   (s/join "\n" (sort aliased-exports)))))

(defn build-page-content
 "Build *.js export file given export information
 #:next{:js-sources :css-imports}"
 [export-info & [dir-css dir-cljs]]
 (let [{:next/keys [js-sources css-imports css-dir cljs-dir]
        :or        {css-dir dir-css, cljs-dir dir-cljs}}
       export-info ;;Extract css-dir and cljs-dir from export-info or from args

       css-imports (build-css-import css-dir css-imports)
       js-imports  (apply str (mapv #(-> %
                                         (assoc :next/cljs-dir cljs-dir)
                                         (build-js-imports))
                                       js-sources))
       js-export (apply build-js-exports js-sources)]
  (s/join "\n" [css-imports js-imports js-export])))

;; END OF BUILD STRING CONTENT
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; BUILD FILES

(defn dump-file!
 "Takes a Java File and spit content into it"
 [out-file content]
 (io/make-parents out-file)
 (spit out-file content)
 (println "Written to " (.getPath out-file)))

;; For Next.js interoperability (:next build)
(defn create-next-page!
 "Create the ./pages/page.js component needed for next.js App tuning"
 [export-info rel-path]
 (let [content (build-page-content export-info)
       out-file (apply io/file rel-path)]
  (dump-file! out-file content)))

;; For assets (:dev build)
(defn create-index-html!
 "Statically generate a index.html file that loads the SPA's javascript"
 [& folders]
 (let [content  (loading-page)
       out-dir  (apply io/file folders)
       out-file (io/file out-dir "index.html")]
  (io/make-parents out-file)
  (spit out-file content)
  (println "Written to " (.getPath out-file))))

;; END OF BUILD FILES
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
