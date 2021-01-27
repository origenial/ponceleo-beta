(ns ponceleo.landing.backend.subscribe.handler
  (:require
   [config.core :as config]
   [ponceleo.landing.backend.status :as status]
   [ponceleo.landing.backend.subscribe.service :refer [prepare-emails]]))


(defn subscribe-enter
  "Adds subscription's :mails-to-send to Pedestal's context"
  [context]
  (let [admin-email (get-in config/env [:mail-server :admin-email])
        dest-email (get-in context [:request :edn-params :email])]
    (assoc-in context
              [:request :mails-to-send]
              (:mails-to-send (prepare-emails dest-email admin-email)))))

(defn subscribe-error
  "Overrides response on subscription error"
  [context]
  (assoc context
         :response
         (status/internal-error
          (str "An internal error occured. "
               "The subscription could not succeed for "
               (get-in context [:request :params :email])))))

(def subscribe-intc
  "HTTP Handler wrapping `ponceleo.landing.subscribe.service/prepare-emails`"
  {:name ::subscribe
   :enter subscribe-enter
   :error subscribe-error})
