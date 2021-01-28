(ns ponceleo.landing.backend.repl
  (:require
   [io.pedestal.http :as http]
   [ponceleo.landing.backend.server :refer [server-state start]]))

(defn start-dev
  "Starts a dev env server that gives back the REPL prompt to the user"
  []
  (start nil 8888))

(defn stop-dev "Stops the dev env server"
  []
  (swap! server-state http/stop)
  nil)

(defn restart "Restarts the dev env server"
  []
  (stop-dev)
  (start-dev))
