(ns origenial.utils.form
  "This namespace aims at defining cross project form utilies"
  (:require
   [cljs.spec.alpha :as spec]
   [clojure.string :refer [blank?]]
   [origenial.utils.core :refer [join-keyword]]))

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


(defn ^:private validity-class
  "Calculate the vality of a field.
   Returns nil if the field is untouched,
   'valid' if it validates the spec,
   'invalid' otherwise."
  [pre-preds spec field]
  (let [not-undefined?
        (cond
          (fn? pre-preds) pre-preds
          (coll? pre-preds) #(every? boolean
                                    ((apply juxt pre-preds) %)))]
  (when (not-undefined? field)
    (if (spec/valid? spec field) "valid" "invalid"))))

(defn with-validity
  "Take a vector of CSS classes and adds the 'valid' or 'invalid' class
  depending on the state of the form-field and its specification"
  ([pre-preds spec field & classes]
   (let [validity (validity-class pre-preds spec field)]
     (into classes (vector validity)))))