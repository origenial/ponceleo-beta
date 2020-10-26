(ns ponceleo.pages.home.component.stat-card)
:div.w-full
(defn stat-card [card]
  [:div {:key (:key card)
         :class ["p-8" "sm:p-4" "md:p-8" "mx-auto" "text-white"]}
   [:img {:src (:image-src card),
          :alt (:image-alt card),
          :class ["w-24" "mx-auto"]
          }]
   [:span {:class ["text-3xl" "font-extrabold" "my-2"]} (:stat-number card)] [:br]
   [:p {:class [""]} (:stat-description card)]
   ])