(ns ponceleo.frontend.router
  "This namespace aims at gathering a router and utilities to manipulate it."
  (:require
   [reitit.frontend :as reitit]))

(def router
  "Routeur Reitit pour la SPA"
  (reitit/router
    [["/" :index]
     ["/about" :about]
     ["/404" :error-404]]))

(defn path-for
  "Define the URI path given a route keyword and params map"
  ([route] (:path (reitit/match-by-name router route)))
  ([route params] (:path (reitit/match-by-name router route params))))

