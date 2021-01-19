(ns ponceleo.landing.frontend.pages.home.component.motto
  "This namespace defines the first thing your are supposed to see when landing
  one the homepage : the Motto of our web SPA."
  )

;; TODO : You definitely need a better style, font, and text for this
(def motto-section
  "'Motto Section' React component consisting of a title and a paragraph"
  [:div {:class ["text-white"]}
   [:h1.text-6xl.text-center.text-bold "Ponceleo"]
   [:h2.text-4xl.text-center
    "Allongez la durée de vie de vos objets"]])
