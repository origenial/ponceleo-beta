(ns user
  (:require [clojure.string :as str]))

(defn ns-starting-with
  "Returns every namespace starting by a pattern"
  [n]
  (->> (all-ns)
       (map ns-name)
       (filter #(str/starts-with? % n ))))

(comment
  (ns-starting-with "ponceleo")

  )
