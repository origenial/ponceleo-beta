(ns ponceleo.landing.common.spec.subscribe-form
  (:require
   #?(:clj [clojure.spec.alpha :as spec]
      :cljs [cljs.spec.alpha :as spec])
   [clojure.string :refer [blank?]]
   [origenial.utils.form :refer [email?]]))

(spec/def ::email
  (spec/and (spec/and string? email? (complement blank?))))
