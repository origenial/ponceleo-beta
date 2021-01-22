(ns ponceleo.landing.frontend.pages.home.component.subscribe-form
  "This namespace defines the 'Subscribe to the newsletter' section
  (as a Reagent component)"
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.string :as str]
   [origenial.utils :refer [mailto-uri]]
   [origenial.utils.form :refer [form-field-changed! with-validity]]
   [ponceleo.landing.common.spec.subscribe-form :as subscribe-spec]
   [reagent.core :as reagent]))

;; -------------------------
;; Utils
(def ^:private nb-space \u00A0)
(def ^:private nb-hyphen \u2011)

(defn build-mail-href
  "Build a subscription mailto URI given a subscriber mail addresses"
  [dest]
  (mailto-uri
   "lyderic.dutillieux@ponceleo.com"
   :subject "Demande d'inscription à la newsletter"
   :body (str/join
          "\n"
          ["Madame, Monsieur,"
           ""
           (str "J'aimerais m'inscrire à la newsletter de Ponceleo avec l'adresse mail : "
                dest)
           ""
           "Je suis intéressé par :"
           "- [X] Les avancées de l'application"
           "- [X] Les avancées de l'entreprise"
           "- [X] Les offres commerciales de Ponceleo"
           ""
           "Merci par avance !"])))

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
      [:div {:class ["text-white"]}
       [:div {:class ["text-center" "mb-0" "xl:mb-8"]}
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
         {:type :button
          :disabled (not form-valid)
          :title (when-not form-valid "Veuillez fournir votre adresse mail")
          :class (into ["p-2" "w-full" "bg-red-500" "text-center"
                        "shadow-sm" "rounded-sm"]
                       (when-not form-valid ["cursor-not-allowed"]))
          :on-click (fn [event]
                      (set! (. js/window -location) (build-mail-href email)))}
         "M'inscrire"]]
       [:div {:class ["w-full" "text-right"]}
        [:span.text-sm "* : Garantie sans spam"]]])))
