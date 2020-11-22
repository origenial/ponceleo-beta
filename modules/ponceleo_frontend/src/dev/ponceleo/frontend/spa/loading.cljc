(ns ponceleo.frontend.spa.loading
  "Namespace for preparing an HTML bootloader for the SPA when working in
   developement environment. This HTML defined in Hiccup syntax aims at
   displaying an host message, loading css and js files of the SPA"
  (:require
   [hiccup.page :refer [include-js include-css html5]]))

(defn head
  "HTML Common Head tag"
  []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css "/css/tailwind.css" "/css/app.css")])

(def pending-body
  "HTML temporary body while the main app.js is loading. (Shadow-cljs)"
  [:div#app
   [:h2 "Welcome to  ponceleo"]
   [:p "The page is loading ..."]
   [:p "Please wait while Shadow-cljs is waking up ... It might take a bit of time"]
   [:p "(Check the js console for hints if nothing exciting happens.)"]])

(defn loading-page
  "HTML temporary loading page when developing with (Shadow-cljs)
   The page has to be copied into an index.html file and is supposed to load
   The css and the js necessary to bootstrap the SPA.
   This is usefull in develop environement when working with Hot Reloaders like
   Shadow-cljs or Fiwheel"
  []
  (html5
    (head)
    [:body pending-body]
    (include-js "/js/out/app.js")))




