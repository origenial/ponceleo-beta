(ns ponceleo.utils.core
  (:require [clojure.string :refer [join]])
  (:require-macros [ponceleo.utils.core]))

(defn join-keyword [& items]
  (->> items
      (map name)
      (join "-")
      keyword))

(defn keyword-in-ns-of
  "Get keyword from the same namespace of first arg"
  [source kw]
  (keyword (namespace source) (name kw)))