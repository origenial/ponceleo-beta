(ns origenial.utils.form
  "This namespace aims at defining cross project form utilies"
  (:require
   #?(:cljs [cljs.spec.alpha :as spec])
   #?(:cljs [origenial.utils.core :refer [join-keyword]])
   [clojure.string :as str]))

(defn email?
  "Predicate that returns true if a string is a valid email"
  [email]
  ;; TODO : Refactor this with Regal : (https://github.com/lambdaisland/regal)
  (let [pattern
        #"[a-za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-za-z0-9](?:[a-za-z0-9-]*[a-za-z0-9])?\.)+[a-za-z0-9](?:[a-za-z0-9-]*[a-za-z0-9])?"]
    (and (string? email) (re-matches pattern email))))

(defn first-match
  "Returns the first group match of a regexp or the fullmatch"
  [re s]
  (let [m (re-matches re s)]
    (cond-> m (vector? m) second)))

(defn full-match
  "Returns the fullmatch of a regexp, regardless of potential inner groups"
  [re s]
  (let [m (re-matches re s)]
    (cond-> m (vector? m) first)))

(defn incoming-int [text]
  (first-match #"-?\d*" text))

(defn incoming-float
  ([text]
   (incoming-float text 2))
  ([text decimals]
   (let [unlocalized (str/replace text "," "." )]
     (full-match (re-pattern (str "-?\\d*\\.?\\d{0," decimals "}0*")) unlocalized))))

#?(:cljs
   (do
     (defn form-field-changed!
       "side effect utility to update a form's atom with current
         textfield on-input-change"
       ([state k]
        (form-field-changed! state k identity))
       ([state k preprocessor & args-rest]
        (fn [e]
          (swap! state assoc
                 k (or (apply preprocessor (.. e -target -value) args-rest)
                       (k @state))
                 :submit nil))))

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

     ))
