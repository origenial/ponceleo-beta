(ns ponceleo.landing.backend.interceptor.email
  [:require
   [config.core :refer [env]]
   [postal.core :as postal]
   [ponceleo.landing.backend.status :as status]])

(defn send-email!
  "Send a unique email given a map of [:from :to :cc :subject :body] and an
  optional mail-server configuration"
  ([mail-to-send]
   (send-email! mail-to-send (env :mail-server)))
  ([mail-to-send server-config]
   (let [sender (or (:from mail-to-send) (:default-sender server-config))
         msg
         (-> mail-to-send
             (assoc :from sender)
             (select-keys [:from :to :cc :bcc :subject :body]))]
     (postal/send-message server-config msg))))

(defn email-sender
  "Handler that retrieves :mails-to-send from the request map and send mails
  through the network"
  ([request]
   (let [mail-server-config (env :mail-server)
         to-send (:mails-to-send request)

         mails-to-send
         (cond-> to-send (map? to-send) (vector))

         results
         (for [mail-to-send mails-to-send]
           (send-email! mail-to-send mail-server-config))] ;; send emails
     (status/ok results "application/edn"))))
