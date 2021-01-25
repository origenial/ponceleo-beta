(ns ponceleo.landing.frontend.core
  "Core utilies orchestrating which page/component should be associated to a
  reitit's router routes.
  This namespace also aims at providing state management mechanisms"
  (:require
   [origenial.utils.react :refer [lazy-component]]
   ["react" :as react]
   [reagent.core :as reagent]))

;; Lazy components definition
(def home ""      (lazy-component ponceleo.landing.frontend.pages.home.index/home-page))
(def about ""     (lazy-component ponceleo.landing.frontend.pages.about.index/about-page))
(def preview ""   (lazy-component ponceleo.landing.frontend.pages.preview.index/preview-page))
(def error-404 "" (lazy-component ponceleo.landing.frontend.pages.error/error-404))


(defn page-for
  "Translate routes into page components"
  [page-name]
  (let [route (if(string? page-name) (keyword page-name) page-name)]
    [:> react/Suspense {:fallback (reagent/as-element [:div "Chargement en cours ..."])}
     (case route
       :index [:> home]
       :about [:> about]
       :preview [:> preview]
       :error-404 [:> error-404]
       [:> error-404])]))
