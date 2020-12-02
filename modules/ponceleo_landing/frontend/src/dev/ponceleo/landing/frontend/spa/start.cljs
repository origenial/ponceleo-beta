(ns ^:dev/once ponceleo.landing.frontend.spa.start
  "SPA's init script when loading the SPA in a browser.
  This script is transpiled to vanilla javascript in a bundled js file.
  This script is called and bootstraped by the statically generated or server
  side rendered HTML file.
  This version of the namespace is reserved for development use only (some
  devtools are added)."
  (:require
   [devtools.core :as devtools]
   [ponceleo.landing.frontend.spa.init :as pinit]))

(when js/goog.DEBUG
  (extend-protocol IPrintWithWriter
    js/Symbol
    (-pr-writer [sym writer _]
      (-write writer (str \" (.toString sym) \"))))
  (devtools/install!)
  (enable-console-print!))

(when-not js/goog.DEBUG
  ;;ignore println statements in prod
  (set! *print-fn* (fn [& _])))

(pinit/init!)
