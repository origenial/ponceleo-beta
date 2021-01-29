(ns ponceleo.landing.backend.route
  "This namespace gathers the route schema definition as well as route specific
  utilities to manipulate this route schema"
  (:require
   #_ [ponceleo.landing.backend.hello.handler :refer [respond-hello echo]]
   [io.pedestal.http.route :as route]
   [ponceleo.landing.backend.interceptor.email :refer [email-sender]]
   [ponceleo.landing.backend.subscribe.handler :refer [subscribe-intc]]))

(def routes
  "Defines the API route schema."
  (route/expand-routes
   #{["/subscribe"
      :post
      [subscribe-intc email-sender]
      :route-name :subscribe]}))
