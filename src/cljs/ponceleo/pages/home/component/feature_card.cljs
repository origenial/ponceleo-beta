(ns ponceleo.pages.home.component.feature-card)


(defn feature-card [card]
  [:div {:key (:key card)
         :class ["w-2/3" "md:w-1/2" "lg:w-1/3" "p-8" "sm:p-4" "md:p-8" "mx-auto"]}
   [:img {:src (:image-src card),
          :alt (:image-alt card),
          :class ["w-1/2" "xl:w-1/3" "mx-auto"]
          }]
   [:h3 {:class ["text-3xl" "my-2"]} (:feature-caption card)]
   [:p {:class ["text-gray-500"]} (:feature-description card)]
   ])

