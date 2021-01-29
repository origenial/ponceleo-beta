(ns ponceleo.landing.backend.server
  (:require
   [config.core :as config]
   [io.pedestal.http :as http]
   [io.pedestal.http.body-params :as bodyp]
   [ponceleo.landing.backend.interceptor.common :as intc]
   [ponceleo.landing.backend.route :refer [routes]]))

(defonce ^{:doc "This atom gather any server information that must persist
                 through requests"}
  server-state (atom nil))

(defn service-map [& [host port]]
  (-> {::http/routes routes
       ::http/type   :jetty
       ::http/host   (or host "127.0.0.1")
       ::http/port   (or port 8890)
       ::http/join? false
       ::http/allowed-origins (select-keys config/env [:allowed-origins])}
      (http/default-interceptors)
      (update ::http/interceptors conj (bodyp/body-params))
      (update ::http/interceptors conj intc/body-coercer)
      (update ::http/interceptors conj intc/content-negotiator)))

(defn start
  "This function bootloads the server with the appropriate service-map
  configuration"
  [& [host port]]
  (reset! server-state
          (http/start (http/create-server (service-map host port))))
  nil)
