(ns ponceleo.landing.backend.status
"This namespace aims at defining utility methods for tuning response with
common status codes and headers")

(defn ok
 "Success 200 : The requested resource was found."
 [body & [content-type]]
 (let [response {:status 200 :body body}]
   (if content-type
     (assoc response :headers {"Content-Type" content-type})
     response)))


(defn not-found
 "Error 404 : The requested resource was not found."
 [& _]
 {:status 400 :body "Not-found\n"})

