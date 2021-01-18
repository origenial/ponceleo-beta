(ns ponceleo.landing.backend.interceptor.email
  [:require
  [config.core :refer [env]]
  [postal.core :as postal]])

(defn send-email!
  "Send a unique email given a map of [:from :to :cc :subject :body] and an
  optional mail-server configuration"
  ([mail-to-send]
   (send-email! mail-to-send (env :mail-server)))
  ([mail-to-send server-config]
   (let [msg (into {}
               (filter (comp some? val)
                 {:from (or (mail-to-send :from)
                          (:default-sender server-config))
                  :to (:to mail-to-send)
                  :cc (:cc mail-to-send)
                  :subject (:subject mail-to-send)
                  :body (:body mail-to-send)}))]
     (postal/send-message server-config msg))))

(def email-sender
  "Interceptor that retrieves :mail-data and send mail through the network"
  {:name :email-sender
   :enter
   (fn [context]
     (let [mail-server-config (env :mail-server)
           to-send (:mails-to-send context)

           mails-to-send
           (cond-> to-send (map? to-send) (vector))

           results
           (for [mail-to-send mails-to-send]
             (send-email! mail-to-send mail-server-config))] ;; send emails
       (assoc context :response {:status 200
                                 :body results})))
   :error
   (fn [context ex-info]
     (assoc context :response {:status 500
                               :body (.getMessage ex-info)}))})