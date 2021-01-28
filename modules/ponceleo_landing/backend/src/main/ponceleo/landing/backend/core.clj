(ns ponceleo.landing.backend.core
  (:require [ponceleo.landing.backend.server :as server])
  (:gen-class))

(defn -main [& [host port & _ :as args]]
  (server/start host (Integer/parseInt port)))
