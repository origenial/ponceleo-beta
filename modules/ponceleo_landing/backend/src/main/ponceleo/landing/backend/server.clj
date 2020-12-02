(ns ponceleo.landing.backend.server
  (:require
    [io.pedestal.http :as http]
    [ponceleo.landing.backend.route :refer [routes]]))

(defonce ^{:doc "This atom gather any server information that must persist
                 through requests"}
  server-state (atom nil))

(def service-map
  "This service-map defines the HTTP Server configuration (Server app, port,
  route handlers,...)"
  {::http/routes routes
   ::http/type   :jetty
   ::http/port   8890
   ::http/join? false})

(defn start
  "This function bootloads the server with the appropriate service-map
  configuration"
  []
  (swap! server-state
    (constantly (http/start (http/create-server service-map))))
  nil)