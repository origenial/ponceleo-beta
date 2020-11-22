(ns ponceleo.frontend.next.pages.app
  "This namespace gathers utilities, classes and methods to create Next.js'
   pages/app.js (Tuning of outer body HTML).
   See Next.js 'Custom App' for more explanations"
  (:require
   ["next/app" :default App]
   [origenial.utils :refer-macros [extend-js-class]]
   [reagent.core :as r]))

(defn next-app-render
  "Custom rendering function for Next.js App Component"
  []
  (this-as ^js/React.Component this
    (let [Component (.. this -props -Component)
          pageProps (.. this -props -pageProps)]
      (r/as-element [:> Component pageProps]))))

(extend-js-class
  ^{:doc "Custom Next.js App ll Component"
    :export true
    :next/page ["_app.js"]
    :next/export-as "default"
    :next/css-imports ["tailwind.css" "app.css"]
    :jsdoc ["@constructor"]}
   next-app App
  {:render next-app-render})


