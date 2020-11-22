(ns origenial.utils.form
  "This namespace aims at defining cross project form utilies"
  (:require
   [cljs.spec.alpha :as spec]
   [origenial.utils.core :refer [join-keyword]]))

(defn email?
  "Predicate that returns true if a string is a valid email"
  [email]
  ;; TODO : Refactor this with Regal : (https://github.com/lambdaisland/regal)
  (let [pattern
        #"[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?"]
    (and (string? email) (re-matches pattern email))))

(defn form-field-changed!
  "Side effect utility to update a form's atom with current
  textfield on-input-change"
  [state k]
  (fn [e]
    (swap! state assoc k (.. e -target -value))))

(defn form-field-focused!
  "Side effect utility to update a form's atom with a boolean indicating
  wether the form field has the focus (on-focus and on-blur)"
  [state key bool]
  (fn [_]
    (swap! state assoc (join-keyword key :focused) bool)))

(defn validity-class
  "Calculate the vality of a field.
   Returns nil if the field is untouched,
   'valid' if it validates the spec,
   'invalid' otherwise."
  [spec field]
  (when (some? field)
    (if (spec/valid? spec field) "valid" "invalid")))

(defn with-validity
  "Take a vector of CSS classes and adds the 'valid' or 'invalid' class
  depending on the state of the form-field and its specification"
  ([spec field & classes]
   (let [validity (validity-class spec field)]
     (into classes (vector validity)))))