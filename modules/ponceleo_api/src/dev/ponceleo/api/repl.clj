(ns ponceleo.api.repl
  (:require
   [io.pedestal.http :as http]
   [ponceleo.api.server :refer [server-state service-map]]))

(defn start-dev []
  (reset! server-state
    (http/start (http/create-server
                  (assoc service-map
                    ::http/join? false)))))

(defn stop-dev []
  (http/stop @server-state))

(defn restart []
  (stop-dev)
  (start-dev))
