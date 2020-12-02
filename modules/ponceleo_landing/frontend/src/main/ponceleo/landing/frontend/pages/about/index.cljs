(ns ponceleo.landing.frontend.pages.about.index
  "This namespace defines the `About` page of the SPA."
  (:require
    [reagent.core :refer [as-element]]))

(defn about-page
  "Returns an Hiccup styled body for the 'About' page"
  {:export true
   :next/page        ["about.js"]
   :next/export-as   "default"}
  []
  (as-element
    [:span.main
     [:h1 "About ponceleo"]]))


