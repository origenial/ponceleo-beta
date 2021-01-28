(ns ponceleo.landing.backend.server
  (:require
    [io.pedestal.http :as http]
    [ponceleo.landing.backend.route :refer [routes]]))

(defonce ^{:doc "This atom gather any server information that must persist
                 through requests"}
  server-state (atom nil))

(defn start
  "This function bootloads the server with the appropriate service-map
  configuration"
  [& [host port]]
  (swap! server-state
         (constantly
          (http/start
           (http/create-server
            {::http/routes routes
             ::http/type   :jetty
             ::http/host   (or host "127.0.0.1")
             ::http/port   (or port 8890)
             ::http/join? false}))))
  nil)
