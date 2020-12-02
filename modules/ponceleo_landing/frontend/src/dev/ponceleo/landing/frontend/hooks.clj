(ns ponceleo.landing.frontend.hooks
  "This namespaces provides Shadow-cljs Build Hooks to automate builds.
  Every Hook function is called within the shadow-cljs.edn config file and is
  given as first arg the current build-state. This build-state can optionnaly
  be modified and MUST be returned for other build steps.
  See Shadow-cljs's documentation about build hooks"
  (:require
    [cljs.compiler :as cljs-comp]
    [clojure.string :refer [join]]
    [origenial.utils :refer [recursive-delete-files]]
    [ponceleo.landing.frontend.build :as pbuild]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; SYSTEM HOOKS

(defn copy-dir!
  "Hook function to automatically copy a dir within another dir"
  [build-state source-dir within-to-dir]
  {:shadow.build/stage :configure}
  (pbuild/copy-dir! source-dir within-to-dir)
  build-state)

;; END OF SYSTEM HOOKS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; JAVASCRIT BUILD HOOKS

(defn ^:private all-vars
  "Gets all top level declared vars and functions given a build state"
  [state]
  (for [[_ ns-info] (get-in state [:compiler-env :cljs.analyzer/namespaces])
        ns-def (-> ns-info :defs vals)]
    ns-def))

(defn ^:private js-source<-ns-def
  "Transforms information from a compiled def into a :next/js-source map"
  [ns-def]
  (let [page-ns   (-> ns-def :name namespace cljs-comp/munge)
        page-var  (-> ns-def :name name cljs-comp/munge)
        export-as (-> ns-def :meta :next/export-as)]
    #:next{:cljs-name page-var
           :cljs-ns page-ns
           :export-as export-as}))

(def ^:private css-imports<-ns-def
  "Transforms information from a compiled def into a :next/css-imports vector"
  (comp :next/css-imports :meta))

(defn create-next-page!
  "Hook function to automatically create ./pages/_app.js for upcoming next.js
   export"
  {:shadow.build/stage :flush}
  [build-state dirs]
  (let [{:keys [page-dir css-dir cljs-dir]} dirs

        compiled-ns-defs
        (all-vars build-state)

        ns-defs-by-page
        (-> #(get-in % [:meta :next/page])
          (group-by compiled-ns-defs)
          (dissoc nil))]

    ;;Delete folder first
    (recursive-delete-files (join "/" page-dir))
    ;;Do for each page to generate
    (doseq [[page-path-from-page-dir ns-defs] ns-defs-by-page
            :let [page-path       (concat page-dir page-path-from-page-dir)
                  page-file-depth (- (count page-path) 1)
                  rel-path-prefix (or (seq (repeat page-file-depth "..")) ["."])
                  css-rel-dir     (concat rel-path-prefix css-dir)
                  cljs-rel-dir    (concat rel-path-prefix cljs-dir)

                  export-info
                  #:next{:css-dir css-rel-dir
                         :cljs-dir cljs-rel-dir
                         :css-imports (mapcat css-imports<-ns-def  ns-defs)
                         :js-sources (map js-source<-ns-def ns-defs)}]]
      (pbuild/create-next-page! export-info page-path)))
  build-state)

;; END OF JAVASCRIPT BUILD HOOKS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; STATIC SITE GENERATION HOOKS

(defn create-index-html!
  "Hook function to render the HTML file necessary to bootstrap the
  Shadow-CLJS  compiled *.js file"
  {:shadow.build/stage :configure}
  [build-state & folders]
  (apply pbuild/create-index-html! folders)
  build-state)

;; END OF STATIC SITE GENERATION HOOKS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;