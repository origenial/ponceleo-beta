(ns ponceleo.landing.backend.contact.handler
  (:require
   [config.core :as config]
   [ponceleo.landing.backend.contact.service :refer [prepare-emails]]
   [ponceleo.landing.backend.status :as status]))

(defn contact-enter
  "Adds contact form's :mails-to-send to Pedestal's context"
  [context]
  (let [contact-email (get-in config/env [:mail-server :contact-email])
        params (get-in context [:request :edn-params])
        trap (pr-str params)
        mails (prepare-emails params contact-email)]
    (assoc-in context [:request :mails-to-send]
              (:mails-to-send mails))))

(defn contact-error
  "Overrides response on contact-form error"
  [context ex-info]
  (assoc context
         :response
         (status/internal-error
          (str "An internal error occured. "
               "The contact form to/from "
               (get-in context [:request :edn-params :email])
               " could not be sent.\n"
               (.getMessage ex-info)))))

(def  contact-intc
  "HTTP Handler wrapping `ponceleo.landing.contact.service/prepare-emails`"
  {:name ::contact
   :enter contact-enter
   :error contact-error})
