(ns ponceleo.landing.backend.subscribe.service
  "This namespace defines basic core functions for the subscription services.
  There should not be a single side effect here, only pure functions."
  (:require [clojure.string :as str]))

(defn prepare-emails
  "Prepare the emails to send for the subscribtion to be effective"
  [dest-email admin-email]
  {:mails-to-send
   {:to dest-email
    :bcc admin-email
    :subject "Inscription à la newsletter de Ponceleo"
    :body
    (str/join
     "\n"
     ["Madame, Monsieur"
      ""
      "C'est avec plaisir que je confirme votre inscription à la newsletter de Ponceleo !"
      "Vous recevrez dès à présent (sans spam) :"
      " - Les avancées de l'application ;"
      " - Les avancées de l'entreprise ;"
      " - Les offres commerciales de Ponceleo."
      ""
      "Bien cordialement,"
      ""
      "Lydéric Dutillieux"
      "Fondateur de Ponceleo"
      admin-email
      ""
      (str "PS : Pour vous désinscrire d'une de ces options ou de la newsletter, envoyez un mail à l'adresse suivante : " admin-email)
      ])
    }})
