(ns ponceleo.frontend.pages.home.component.subscribe-form
  "This namespace defines the 'Subscribe to the newsletter' section
  (as a Reagent component)")

;; -------------------------
;; Utils
(def ^:private nb-space \u00A0)
(def ^:private nb-hyphen \u2011)

(def subscribe-section
  [:div {:class ["text-white" "lg:ml-8"]}
   [:div {:class ["text-center" "mb-4"]}
    [:span {:class ["text-3xl"]} "Testez en "]
    [:span {:class ["text-3xl font-extrabold break-normal"]}
     "avant" nb-hyphen "première" nb-space "!"]]
   [:span "Et recevez notre newsletter*"]
   [:input {:id          "email-input",
            :type        :email,
            :placeholder "Adresse email",
            :class       ["p-2" "w-full" "mb-2"]}]
   [:button {:class ["p-2" "w-full" "bg-red-500" "text-center"]} "M'inscrire"]
   [:div {:class ["w-full" "text-right"]}
    [:span "* : Garantie sans spam"]]])

