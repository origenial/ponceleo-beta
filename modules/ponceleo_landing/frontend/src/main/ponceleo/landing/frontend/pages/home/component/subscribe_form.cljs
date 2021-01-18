(ns ponceleo.landing.frontend.pages.home.component.subscribe-form
  "This namespace defines the 'Subscribe to the newsletter' section
  (as a Reagent component)"
  (:require
   [clojure.spec.alpha :as spec]
   [origenial.utils.form :refer [form-field-changed! with-validity]]
   [ponceleo.landing.common.spec.subscribe-form :as subscribe-spec]
   [reagent.core :as reagent]))

;; -------------------------
;; Utils
(def ^:private nb-space \u00A0)
(def ^:private nb-hyphen \u2011)

(defonce
  ^{:doc "State of the subscription form, defined once to persist through
  re-renderings"
    :private true}
  form-state
  (reagent/atom {:email nil}))

(defn subscribe-section
  "'Newsletter Subscription Form' Reagent Component"
  []
  (fn []
    (let [email      (:email @form-state)
          form-valid (spec/valid? ::subscribe-spec/email email)]
      [:div {:class ["text-white" "lg:ml-8"]}
       [:div {:class ["text-center" "mb-8"]}
        [:span {:class ["text-3xl"]} "Testez en "]
        [:span {:class ["text-3xl font-extrabold break-normal"]}
         "avant" nb-hyphen "première" nb-space "!"] [:br]
        [:span.text-sm "Et recevez notre newsletter*"]]
       [:form.text-lg.mt-4 {:no-validate true
                            :style {"--form-inactive-color" "#FFFFFF"
                                    "--form-neutral-color" "#FFFFFF"
                                    "--form-valid-color" "#77FF77"
                                    "--form-invalid-color" "#FF7777"
                                    "--form-zoom" 1.125}}
        [:div.form-group
         [:input.bg-gray-500.bg-opacity-0
          {:name :email,
           :type :email,
           :required true,
           :class (with-validity not-empty
                    ::subscribe-spec/email email
                    "p-2" "w-full")
           :value email
           :on-change (form-field-changed! form-state :email)}]
         [:label "Adresse mail" [:span.hint "Exemple : test@test.com"]]
         [:span.bar]]
        [:button#subscribe-form-submit
         {:disabled (not form-valid)
          :title (when-not form-valid "Veuillez fournir votre adresse mail")
          :class (into ["p-2" "w-full" "bg-red-500" "text-center"]
                       (when-not form-valid ["cursor-not-allowed"]))
          :on-click (fn [event]
                      (.preventDefault event)
                      (.stopPropagation event))}
         "M'inscrire"]]
       [:div {:class ["w-full" "text-right"]}
        [:span.text-sm "* : Garantie sans spam"]]])))
