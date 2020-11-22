(ns origenial.utils
  "Namespace for cross project utilities"
  (:require
    [clojure.java.io :as io]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; SYSTEM UTILS

(defn recursive-delete-files
  "Deletes recursiveley every files from a directory"
  [& fs]
  (when-let [f (first fs)]
    (if-let [cs (seq (.listFiles (io/file f)))]
      (recur (concat cs fs))
      (do (io/delete-file f)
          (recur (rest fs))))))

;; END OF SYSTEM UTILS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; CORE UTILS

(defn update-many
  "Create a new map but apply a function to some keys of the map.
   If (seq keys) evaluates to nil, then the function is applied to every key"
  [m ks f & args]
   (reduce #(apply update %1 (keyword %2) f args) m (or (seq ks) (keys m))))

;; END OF CORE UTILS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; JAVASCRIPT INTEROP UTILS

(defmacro extend-js-class
  "Creates an ES6 compatible subclass"
  [sub-class base-class method-map
   & [inherit-static-method? static-method-overrides-map]]
  (list
    'do
    ;;Constructor
    `(defn ~sub-class []
       (js/Reflect.construct ~base-class (clojure.core/clj->js []) ~sub-class))

    ;;Methods
    ;; Create subclass prototype
    `(set!
       (.-prototype ~sub-class)
       ;; Use Object.create to retain prototype hierarchy
       ;; (simulates sub-class extends base-class)
       (js/Object.create (.-prototype ~base-class)
         (clojure.core/clj->js
           ~(update-many
             method-map #{}
             (partial hash-map :configurable true :value)))))

    ;; Inherit static methods if required
    (when inherit-static-method?
      `(doseq [prop-name# (js/Object.getOwnPropertyNames ~base-class)
               :let [prop-value# (goog.object/get ~base-class prop-name#)]
               :when (fn? prop-value#)]
         (goog.object/set ~sub-class prop-name# prop-value#)))

    ;; Override or defines static methods
    `(doseq [[fname# f#] (or ~static-method-overrides-map {})
             :when (fn? f#)]
       (goog.object/set ~sub-class (name fname#) f#))))

;; END OF JAVASCRIPT INTEROP UTILS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;