(ns ponceleo.landing.backend.hello.handler
  "Gathers handler wrapper around core services to define a RESTFul API"
  (:require
   [ponceleo.landing.backend.hello.service :refer :all]
   [ponceleo.landing.backend.status :refer :all]))


(defn respond-hello
  "HTTP Handler wrapping `ponceleo.landing.backend.hello.service/greeting-for`"
  [request]
  (let [nm   (get-in request [:query-params :name])
        resp (greeting-for nm)]
    (ok resp)))

;; This is an interceptor's map. When building the route, this maps gets
;; transformed into an Interceptor
;; The :name is an id for debugging,
;; The :enter function takes a context and returns a context
(def echo
  "HTTP Handler wrapping `ponceleo.landing.backend.hello.service/echo`"
  {:name ::echo
   :enter #(assoc % :response (ok (:request %)))})
