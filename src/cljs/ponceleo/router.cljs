(ns ponceleo.router
  (:require [reitit.frontend :as reitit]))


;; -------------------------
;; Routes

(def router
  (reitit/router
    [["/" :index]
     ["/about" :about]
     ["/error/404-not-found" :error-404]]))

(defn path-for [route & [params]]
  (if params
    (:path (reitit/match-by-name router route params))
    (:path (reitit/match-by-name router route))))