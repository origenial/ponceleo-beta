(ns origenial.utils
  "Namespace for cross-project utilities. May refer to macros defined in
  *.clj{c,} files"
  (:require [clojure.string :as str])
  (:require-macros [origenial.utils]))

(defn query-string
  ([k v] (js/encodeURI (str (name k) \= v)))
  ([kvs] (->> kvs
              (remove (comp nil? val))
              (mapv (partial apply query-string))
              (str/join "&"))))

(defn mailto-uri [to & {:keys [subject from cc bcc body] :as args}]
  (str "mailto:" to "?" (query-string args)))
