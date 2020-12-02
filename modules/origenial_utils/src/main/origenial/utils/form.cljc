(ns origenial.utils.form)

(defn email?
  "Predicate that returns true if a string is a valid email"
  [email]
  ;; TODO : Refactor this with Regal : (https://github.com/lambdaisland/regal)
  (let [pattern
        #"[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?"]
    (and (string? email) (re-matches pattern email))))