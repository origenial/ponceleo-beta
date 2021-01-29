(ns ponceleo.landing.backend.subscribe.service
  "This namespace defines basic core functions for the subscription services.
  There should not be a single side effect here, only pure functions."
  (:require [clojure.string :as str]
            [hiccup.page :refer [html5]]))

(defn prepare-emails
  "Prepare the emails to send for the subscribtion to be effective"
  [{:keys [email]} admin-email]
  {:mails-to-send
   {:to email
    :bcc admin-email
    :subject "Confirmation d'inscription à la newsletter de Ponceleo"
    :body
    [{:type "text/html; charset=utf-8"
      :content
      (html5
       {:lang "fr"}
       [:div
        [:span "Madame, Monsieur,"] [:br]
        [:p
         "C'est avec plaisir que je " [:b "confirme votre inscription à la newsletter de Ponceleo !"] [:br]
         "Dès à présent, vous recevrez (sans spam) à l'adresse " email " :"
         [:ul
          [:li "Les avancées de l'application ;"]
          [:li "Les avancées de l'entreprise ;"]
          [:li "Les offres commerciales de Ponceleo."]]]
        [:p "Bien cordialement,"]
        [:div
         [:span [:b "Lydéric Dutillieux,"]] [:br]
         [:span "Fondateur de Ponceleo"] [:br]
         [:span admin-email]]]
       [:p
        "PS : Pour vous désinscrire d'une de ces options ou de la newsletter, "
        "envoyez un mail à l'adresse suivante : "
        admin-email])}]}})
