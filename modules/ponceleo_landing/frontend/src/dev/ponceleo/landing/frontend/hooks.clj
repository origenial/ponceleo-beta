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

(defn create-index-html!
  "Hook function to render the HTML file necessary to bootstrap the
  Shadow-CLJS  compiled *.js file"
  {:shadow.build/stage :configure}
  [build-state & folders]
  (apply pbuild/create-index-html! folders)
  build-state)

;; END OF STATIC SITE GENERATION HOOKS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
