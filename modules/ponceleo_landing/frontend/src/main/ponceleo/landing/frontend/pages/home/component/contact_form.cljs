(ns ponceleo.landing.frontend.pages.home.component.contact-form
  "This namespace defines the 'Contact Form' Reagent Component and
  interactions."
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [cljs-http.client :as http]
   [cljs.core.async :refer [<!]]
   [clojure.spec.alpha :as spec]
   [origenial.utils.form :refer [form-field-changed! with-validity]]
   [ponceleo.landing.common.spec.contact-form :as form-spec]
   [ponceleo.landing.frontend.core :as core]
   [reagent.core :as reagent]))


(defn ok? "True if response status is ok"
  [response] (<= 200 (:status response) 251))

(defn api-contact
  "Call /contact api with contact-form params"
  [params]
  (let [params (select-keys params [:full-name :email :subject :message])]
    (http/post (str core/API_URL "/contact")
               {:with-credentials? false, :edn-params params})))

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
                 :message   nil
                 :submit    nil}))

(defn contact-form
  "'Contact Form' Reagent Component"
  []
  (fn []
    (let [{:keys [full-name email subject message submit]} @form-state
          form-valid (spec/valid? ::form-spec/contact-form @form-state)
          submit-disabled (or (#{:loading :success} submit) (not form-valid))
          submit-message (case submit
                           :loading "◌ Chargement..."
                           :success "✔ Envoyé !"
                           :error   "⚠ Erreur ! Réessayer"
                           "Envoyer")]
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
       [:button#contact-form-submit.my-8,
        {:type :button
         :class ["p-2" "text-white" "w-1/2"
                 "bg-red-500" "text-center"
                 "shadow-md" "rounded-sm"]
         :on-click
         (fn [event]
           (let [init-blank #(or % "")
                 init-untouched #(-> %
                                     (update :full-name init-blank)
                                     (update :email init-blank)
                                     (update :message init-blank)
                                     (update :subject init-blank))]
             (when-not submit-disabled
               (swap! form-state assoc :submit :loading)
               (go (let [response (<! (api-contact @form-state))]
                     (swap! form-state assoc :submit
                            (if (ok? response)
                              :success
                              :error)))))
             (when submit-disabled
               (swap! form-state init-untouched)))
           )}
        submit-message]]))
  )
