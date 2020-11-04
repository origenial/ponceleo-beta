(ns ponceleo.handler
  (:require
   [hiccup.page :refer [include-js include-css html5]]
   [config.core :refer [env]]))

(declare head pending-body)

(defn loading-page []
  (html5
   (head)
   [:body
    pending-body]
    (include-js "/js/out/app.js")))

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css "/css/tailwind.css" "/css/app.css")])

(def pending-body
  [:div#app
   [:h2 "Welcome to ponceleo"]
   [:p "The page is loading ..."]
   [:p "Please wait while Shadow-cljs is waking up ... It might take a bit of time"]
   [:p "(Check the js console for hints if nothing exciting happens.)"]])
