(ns ^{:figwheel-no-load true, :figwheel-hooks true} ponceleo.dev
  (:require
    [ponceleo.core :as core]
    [devtools.core :as devtools]))

(extend-protocol IPrintWithWriter
  js/Symbol
  (-pr-writer [sym writer _]
    (-write writer (str "\"" (.toString sym) "\""))))

(devtools/install!)

(enable-console-print!)

(core/init!)

(defn ^:after-load re-render []
      (core/mount-root))
