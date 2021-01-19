(ns ponceleo.landing.frontend.build
  "This namespace is reserved for build or code generation utilities.
  It is thought to get invokable from an REPL session or by clojurish scripts"
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s :refer [join]]
   [me.raynes.fs :refer [mkdirs copy-dir delete-dir]]
   [ponceleo.landing.frontend.loading :refer [loading-page]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; SYSTEM UTILITIES

(defn copy-dir!
  "Utility function to copy a dir within another dir after cleaning the target
  dir"
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
