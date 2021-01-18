(ns ponceleo.core.backend.email.service
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
