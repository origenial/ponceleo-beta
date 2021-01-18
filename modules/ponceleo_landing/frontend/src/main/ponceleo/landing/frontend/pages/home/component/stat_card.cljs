(ns ponceleo.landing.frontend.pages.home.component.stat-card
  "This namespace defines a 'Statistic card' Reagent component")

(defn stat-card
  "'Statistic card' React component consisting of :
  an image, a caption and a description"
  [card]
  [:div {:key (:key card)
         :class ["p-8" "sm:p-4" "md:p-8" "mx-auto" "text-white"]}
   [:img {:src (:image-src card),
          :alt (:image-alt card),
          :class ["w-24" "mx-auto"]}]
   [:figcaption {:class ["text-3xl" "font-extrabold" "my-2"]} (:stat-number
                                                               card)]
   [:p {:class [""]} (:stat-description card)]])
