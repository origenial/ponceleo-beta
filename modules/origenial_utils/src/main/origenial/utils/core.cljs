(ns origenial.utils.core
  "Namespace gathering cross project core utilities "
  (:require [clojure.string :refer [join]]))

(defn join-keyword
  "Joins each arg's name with a dash and keywordize the  result.
   Example : `(join-keyword :hello :world)` yields `:hello-world` "
  [& items]
  (->> items
      (map name)
      (join "-")
      keyword))

(defn keyword-in-ns-of
  "Returns the keyword as if it came from the namespace of the first arg.
   Example : `(keyword-in-ns-of :foo/bar :bar/baz)` yields `:foo/baz`."
  [source kw]
  (keyword (namespace source) (name kw)))