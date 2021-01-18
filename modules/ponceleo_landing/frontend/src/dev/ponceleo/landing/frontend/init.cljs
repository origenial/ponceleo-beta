(ns ponceleo.landing.frontend.init
  "This namespace provides utility functions to bootload the SPA application"
  (:require
   [accountant.core :as accountant]
   [clerk.core :as clerk]
   [ponceleo.landing.frontend.core :refer [page-container]]
   [ponceleo.landing.frontend.router :refer [router]]
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [reagent.session :as session]
   [reitit.frontend :as reitit]))

;; -------------------------
;; Initialize app

(defn current-page
  "Extracts current page from current Browser URL,
   guess the right component thanks to SPA's Reitit router,
   and returns it."
  []
  (fn [] [page-container (:current-page-name (session/get :route))]))

(defn ^:dev/after-load mount-root
  "Mount the current page as react component into the body of the page"
  []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init!
  "Init script to play once the SPA is loaded.
   It makes mount the SPA and makes its navigation consistent with basic
   static website browser navigation. More precisely, this init script
   enables  session updates (current URI), scroll consistency, navigation
   history (browser API to navigate backward), ..."
  []
  (clerk/initialize!)
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (let [match             (reitit/match-by-path router path)
             current-page-name (:name (:data match))
             route-params      (:path-params match)]
         (reagent/after-render clerk/after-render!)
         (session/put! :route {:current-page-name current-page-name
                               :route-params route-params})
         (clerk/navigate-page! path)))

     :path-exists?
     (fn [path]
       (boolean (reitit/match-by-path router path)))})
  (accountant/dispatch-current!)
  (mount-root))
