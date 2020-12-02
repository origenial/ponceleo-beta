(ns ponceleo.landing.common.spec.contact-form
  (:require
    [clojure.spec.alpha :as spec]
    [clojure.string :refer [trim split]]
    [origenial.utils.form :refer [email?]]))

(spec/def ::full-name
  (spec/and string? not-empty))
(spec/def ::email
  (spec/and string? email? not-empty))
(spec/def ::message
  (spec/and string? not-empty #(-> (trim %)
                                   (split #"\s")
                                   (partial filter not-empty)
                                   count
                                   (>= 5))))
(spec/def ::contact-form
  (spec/keys :req-un [::full-name ::email ::message]))

