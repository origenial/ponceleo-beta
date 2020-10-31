(ns ponceleo.pages.home.component.contact-form
  (:require [ponceleo.utils.core :refer [join-keyword keyword-in-ns-of]]
            [ponceleo.utils.form :refer [email? form-field-changed!]]
            [clojure.string :refer [blank? trim split]]
            [reagent.core :as reagent]
            [clojure.spec.alpha :as spec]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; FORM VALIDATION SPECIFICATION  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(spec/def ::full-name (spec/and string? not-empty))
(spec/def ::email (spec/and string? email? not-empty))
(spec/def ::message (spec/and string? not-empty #(-> % trim (split #"\s") count (>= 5))))
(spec/def ::contact-form (spec/keys :req-un [::full-name ::email ::message]))

(def user-hints {
                  :full-name "Requis",
                  :email "Exemple : test@test.com",
                  :message "5 mots minimum",
                  })

(defonce form-state (reagent/atom {}))

(defn contact-form []
  (fn []
    (let [
        full-name (:full-name @form-state)
        email (:email @form-state)
        message (:message @form-state)
        contact-form-is-valid (spec/valid? ::contact-form @form-state)
        ]
      [:form {:no-validate true}
       [:div.form-group
         [:input {:name        :full-name,
                  :type        :text,
                  :required    true,
                  :class       (conj ["w-full"] (when (some? full-name) (if (spec/valid? ::full-name full-name) "valid" "invalid")))
                  :value       full-name
                  :on-change   (form-field-changed! form-state :full-name)
                  }]
          [:label "Nom complet" [:span.hint (:full-name user-hints)]]
          [:span.bar]]
       [:div.form-group
        [:input { :name        :email,
                  :type        :email,
                  :required    true,
                  :class       (conj ["w-full"] (when (some? email) (if (spec/valid? ::email email) "valid" "invalid")))
                  :value       email
                  :on-change   (form-field-changed! form-state :email)
                }]
          [:label "Adresse email" [:span.hint (:email user-hints)]]
          [:span.bar]]
       [:div.form-group
        [:textarea {:name          :message,
                    :required      true,
                   :class       (conj ["w-full" "resize-y"] (when (some? message) (if (spec/valid? ::message message) "valid" "invalid"))),
                   :style       {:min-height "128px"},
                   :value       message,
                   :on-change   (form-field-changed! form-state :message)
                   }]
        [:label "Message" [:span.hint (:message user-hints)]]
        [:span.bar]]
        [:button {:id    :contact-form-submit,
                 :disabled (not contact-form-is-valid),
                 :class (apply conj ["p-2" "w-1/2" "bg-red-500" "text-center" "text-white"] (if-not contact-form-is-valid ["opacity-50 cursor-not-allowed"]) )
                 :on-click (fn [event]
                             (.preventDefault event)
                             (.stopPropagation event)
                             (for [k ['full-name 'email 'message]]
                               (swap! form-state assoc (keyword k) (or (eval k) "")))
                 )}
        "Envoyer"]])))