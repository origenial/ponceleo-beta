(ns ponceleo.landing.frontend.pages.home.component.contact-form
  "This namespace defines the 'Contact Form' Reagent Component and
  interactions."
  (:require
    [clojure.spec.alpha :as spec]
    [origenial.utils.form :refer [form-field-changed! with-validity]]
    [ponceleo.landing.common.spec.contact-form :as form-spec]
    [reagent.core :as reagent]))

(def ^:private user-hints
  "Defines the error/hint messages that need to be displayed when the form's
  text-field is invalid."
  {:full-name "Requis",
   :email "Exemple : test@test.com",
   :message "5 mots minimum",
   })

(defonce
  ^{:doc "State of the contact form, defined once to persist through
  re-renderings"
    :private true}
  form-state
  (reagent/atom {:full-name nil
                 :email     nil
                 :message   nil}))

(defn contact-form
  "'Contact Form' Reagent Component"
  []
  (fn []
    (let [
          full-name  (:full-name @form-state)
          email      (:email @form-state)
          message    (:message @form-state)
          valid-form (spec/valid? ::form-spec/contact-form @form-state)
          ]
      [:form {:no-validate true
              :style {"--form-zoom" 1.125}}
       ;; FULL-NAME FIELD
       [:div.form-group
        [:input {:name :full-name,
                 :type :text,
                 :required true,
                 :class (with-validity some?
                                       ::form-spec/full-name full-name
                                       "w-full")
                 :value full-name
                 :on-change (form-field-changed! form-state :full-name)
                 }]
        [:label "Nom complet" [:span.hint (:full-name user-hints)]]
        [:span.bar]]
       ;; EMAIL FIELD
       [:div.form-group
        [:input {:name :email,
                 :type :email,
                 :required true,
                 :class (with-validity some?
                                       ::form-spec/email email
                                       "w-full")
                 :value email
                 :on-change (form-field-changed! form-state :email)
                 }]
        [:label "Adresse email" [:span.hint (:email user-hints)]]
        [:span.bar]]
       ;; MESSAGE FIELD
       [:div.form-group
        [:textarea {:name :message,
                    :required true,
                    :class (with-validity some?
                                          ::form-spec/message message
                                          "w-full" "resize-y"),
                    :value message,
                    :on-change (form-field-changed! form-state :message)
                    }]
        [:label "Message" [:span.hint (:message user-hints)]]
        [:span.bar]]
       [:button#contact-form-submit,
        {:disabled (not valid-form),
         :class (into ["p-2" "text-white" "w-1/2"
                       "bg-red-500" "text-center"]
                  (when-not valid-form
                    ["opacity-50 cursor-not-allowed"]))
         :on-click (fn [event]
                     (.preventDefault event)
                     (.stopPropagation event)
                     (for [k ['full-name 'email 'message]]
                       (swap! form-state
                         assoc (keyword k) (or (eval k) ""))))}
        "Envoyer"]])))