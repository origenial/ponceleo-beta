(ns ponceleo.landing.common.exception
  (:require [cljstache.core :refer [render]]))

(defn- timestamp-now!
  "Get the current timestamp"
  []
  #?(:clj (quot (System/currentTimeMillis) 1000)
     :cljs (.now js/Date)))

(def ^:private exception-template
  "Takes an exception :type and returns a template message"
  {
    :default "An unknown exception occurred. Please contact your administrator."
  })

(defn create-exception
  "Create a lightweight exception EDN object that can be shared through the
  network"
  [type info & [timestamp]]
  (let [template (or (exception-template type)
                     (exception-template :default))
        t (or timestamp (timestamp-now!))]
  (hash-map
   :type type
   :timestamp t
   :info info
   :template template
   :message (render template info))))