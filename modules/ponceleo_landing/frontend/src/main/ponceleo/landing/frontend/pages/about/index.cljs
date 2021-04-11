(ns ponceleo.landing.frontend.pages.about.index
  "This namespace defines the `About` page of the SPA."
  (:require [ponceleo.landing.frontend.router :refer [path-for]]))

(defn about-page
  "Returns an Hiccup styled body for the 'About' page"
  []
  [:div#about
   [:h1 "À propos de Ponceleo"]
   [:table
    [:tbody
     [:tr
      [:th {:scope "row"} "Publication"]
      [:td "Lydéric Dutillieux"]]
     [:tr
      [:th {:scope "row"} "Mail"]
      [:td "lyderic.dutillieux@ponceleo.com"]]
     [:tr
      [:th {:scope "row"} "Téléphone"]
      [:td "+33651962453"]]
     [:tr
      [:th {:scope "row"} "Adresse"]
      [:td "27, Rue Morand, 75011 Paris"]]
     [:tr
      [:th {:scope "row"} "Hébergeur"]
      [:td "OVH - +33972101007 - www.ovh.com/fr/"]]
     [:tr
      [:th {:scope "row"} "Cookies"]
      [:td "Aucun"]]]]

   [:a {:href (path-for :index)} "Retourner à l'accueil principal"]]
  )
