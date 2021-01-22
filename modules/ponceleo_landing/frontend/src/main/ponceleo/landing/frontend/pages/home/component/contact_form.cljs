(ns ponceleo.landing.frontend.pages.home.component.contact-form
  "This namespace defines the 'Contact Form' Reagent Component and
  interactions."
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.string :as str]
   [origenial.utils :refer [mailto-uri]]
   [origenial.utils.form :refer [form-field-changed! with-validity]]
   [ponceleo.landing.common.spec.contact-form :as form-spec]
   [reagent.core :as reagent]))

(defn build-mail-href
  "Build a subscription mailto URI given a subscriber mail addresses"
  [fullname email subject message]
  (mailto-uri
   "lyderic.dutillieux@ponceleo.com"
   :subject subject
   :bcc email
   :body (str/join
          "\n"
          [message
           ""
           (str fullname ",")
           email])))

(def ^:private user-hints
  "Defines the error/hint messages that need to be displayed when the form's
  text-field is invalid."
  {:full-name "Requis",
   :email "Exemple : test@test.com",
   :subject "Requis",
   :message "5 mots minimum",
   })

(defonce
  ^{:doc "State of the contact form, defined once to persist through
  re-renderings"
    :private true}
  form-state
  (reagent/atom {:full-name nil
                 :email     nil
                 :subject   nil
                 :message   nil}))

(defn contact-form
  "'Contact Form' Reagent Component"
  []
  (fn []
    (let [
          full-name  (:full-name @form-state)
          email      (:email @form-state)
          subject    (:subject @form-state)
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
       ;; SUBJECT FIELD
       [:div.form-group
        [:input {:name :subject,
                 :type :text,
                 :required true,
                 :class (with-validity some?
                          ::form-spec/subject subject
                          "w-full")
                 :value subject
                 :on-change (form-field-changed! form-state :subject)
                 }]
        [:label "Sujet" [:span.hint (:subject user-hints)]]
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
        {:type :button
         :disabled (not valid-form),
         :class (into ["p-2" "text-white" "w-1/2"
                       "bg-red-500" "text-center"
                       "shadow-md" "rounded-sm"]
                      (when-not valid-form
                        ["opacity-50 cursor-not-allowed"]))
         :on-click (fn [event]
                     (set! (. js/window -location)
                           (build-mail-href full-name email subject message))
                     )}
        "Envoyer"]]))
  )
