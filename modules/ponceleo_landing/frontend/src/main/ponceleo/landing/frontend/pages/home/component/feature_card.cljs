(ns ponceleo.landing.frontend.pages.home.component.feature-card
  "This namespace defines a 'Feature card' Reagent component")


(defn feature-card
  "'Feature card' Reagent Component to present the features of the SPA.
  Consists of an image, a caption and a description"
  [card]
  [:div {:key (:key card)
         :class ["w-full" "md:w-1/2" "lg:w-1/3" "p-8" "sm:p-4" "md:p-8"
                 "mx-auto"]}
   [:img {:src (:image-src card),
          :alt (:image-alt card),
          :class ["w-1/2" "xl:w-1/3" "mx-auto"]
          }]
   [:figcaption {:class ["text-3xl" "my-2"]} (:feature-caption card)]
   (into [:p {:class ["text-gray-500"]}] (:feature-description card))
   ])
