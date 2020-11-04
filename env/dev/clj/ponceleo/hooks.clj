(ns ponceleo.hooks
  (:require [ponceleo.user :as puser ]))

(defn create-index-html
  {:shadow.build/stage :flush}
  [build-state & folders]
  (apply puser/create-index-html folders)
  build-state)