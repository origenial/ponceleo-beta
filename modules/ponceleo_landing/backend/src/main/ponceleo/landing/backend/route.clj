(ns ponceleo.landing.backend.route
"This namespace gathers the route schema definition as well as route specific
 utilities to manipulate this route schema"
  (:require
    [io.pedestal.http.route :as route]
    [ponceleo.landing.backend.hello.handler :refer [respond-hello echo]]
    [ponceleo.landing.backend.interceptor.common :as intc ]))

(def routes
  "Defines the API route schema."
  (route/expand-routes
    #{["/greet"
       :get [intc/coerce-body intc/content-neg respond-hello]
       :route-name :greet]
      ["/echo"
       :get echo]}))