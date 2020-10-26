(ns ponceleo.utils.form)

(defn email?
  [email]
  (let [pattern #"[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?"]
    (and (string? email) (re-matches pattern email))))

(defn form-field-changed! [state k]
  (fn [e]
    (swap! state assoc k (.. e -target -value))))

(defn form-field-focused! [state key bool]
  (fn [e]
    (swap! state assoc (join-keyword key :focused) bool)))