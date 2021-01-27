(ns ponceleo.landing.backend.status
"This namespace aims at defining utility methods for tuning response with
common status codes and headers")

(defn ok
  "Success 200 : The requested resource was found."
  [body & [content-type]]
  (let [response {:status 200 :body body}]
    (cond-> response
      content-type
      (assoc-in [:headers "Content-Type"] content-type))))


(defn not-found
  "Error 404 : The requested resource was not found."
  [& _]
  {:status 400 :body "Not-found\n"})

(defn internal-error
  "Error 500 : Something went wrong on our server"
  [& msg]
  {:status 500
   :body
   (or msg
       "Something went wrong on our server. We could not process your request")})
