(ns ponceleo.landing.backend.contact.service
  "This namespace defines basic core functions for the contact form services.
  There should not be a single side effect here, only pure functions."
  (:require [hiccup.page :refer [html5]]
            [clojure.string :as str]))

(defn contact-form-content
  "Returns a hiccup view of the contact form"
  [{:keys [full-name email subject message]}]
  [:div {:style "border: 1px solid black; padding:10px; max-width:600px;"}
   [:span [:b "Nom complet : "] full-name] [:br]
   [:span [:b "Adresse email : "] email] [:br]
   [:span [:b "Sujet : "] subject] [:br]
   [:span [:b "Message : "]]
   [:p (interpose  [:br] (str/split message #"\r?\n"))]])

(defn prepare-emails
  "Returns email to send, given email params"
  [{:keys [full-name email subject message] :as form} contact-email]
  {:mails-to-send
   [{:to email
     :subject "Confirmation de réception du formulaire de contact de Ponceleo"
     :body
     [{:type "text/html; charset=utf-8"
       :content
       (html5
        {:lang "fr"}
        [:div
         [:span "Madame, Monsieur,"] [:br]
         [:p
          "Je vous confirme bonne réception de votre formulaire de contact." [:br]
          "Nous vous répondrons dès que possible." [:br]
          "Vous trouverez ci-dessous ce que nous avons reçu de votre part :"]
         (contact-form-content form)
         [:p "Bien cordialement,"]
         [:div
          [:span [:b "Lydéric Dutillieux,"]] [:br]
          [:span "Fondateur de Ponceleo"] [:br]
          [:span contact-email]]])}]}
    {:to contact-email
     :subject (str "[Formulaire contact] " subject)
     :body
     [{:type "text/html; charset=utf-8"
       :content
       (html5
        {:lang "fr"}
        (contact-form-content form))}]}]})
