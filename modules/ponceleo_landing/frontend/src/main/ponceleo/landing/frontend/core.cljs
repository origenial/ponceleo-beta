(ns ponceleo.landing.frontend.core
  "Core utilies orchestrating which page/component should be associated to a
  reitit's router routes.
  This namespace also aims at providing state management mechanisms"
  (:require
   [ponceleo.landing.frontend.pages.about.index :refer [about-page]]
   [ponceleo.landing.frontend.pages.error :refer [error-404]]
   [ponceleo.landing.frontend.pages.home.index :refer [home-page]]
   [ponceleo.landing.frontend.pages.preview.index :refer [preview-page]]))

(defn page-for
  "Translate routes into page components"
  [page-name]
  (let [route (if(string? page-name) (keyword page-name) page-name)]
    (case route
      :about #'about-page
      :index #'home-page
      :preview #'preview-page
      :error-404 #'error-404
      #'error-404)))
