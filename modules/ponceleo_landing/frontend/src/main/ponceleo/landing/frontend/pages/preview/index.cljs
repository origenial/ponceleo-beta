(ns ponceleo.landing.frontend.pages.preview.index
  (:require [clojure.spec.alpha :as spec]
            [origenial.utils.form
             :refer
             [form-field-changed! incoming-float incoming-int with-validity]]
            [ponceleo.landing.frontend.router :refer [path-for]]
            [reagent.core :as reagent]))

;;;;;;;;;;;;;;
;; SPECS
;;;;;;;;;;;;;;

(def current-year (.getFullYear (js/Date.)))

(spec/def ::name
  (spec/and string? not-empty))
(spec/def ::year
  (spec/and integer? (partial <= 1800) (partial >= current-year)))
(spec/def ::price
  (spec/and number? pos?))
(spec/def ::belonging
  (spec/keys :req-un [::name ::year ::price]))


(def ^:private hints
  "Defines the error/hint messages that need to be displayed when the form's
  text-field is invalid."
  {:name "Requis",
   :year (str "1800-" current-year),
   :price "Positif",
   })

;;;;;;;;;;;;;;
;; VIEWS
;;;;;;;;;;;;;;

(defn belonging-card
  "Create an object tile from an object item"
  [{:keys [name year price] :as belonging}
   {:keys [on-edit-request on-delete-request on-up on-down] :as handler}]
  [:div.belonging
   [:div.intents
    [:button
     {:type "button"
      :aria-label "Editer"
      :title "Editer"
      :on-click
      (fn [e] (on-edit-request belonging))}
     "✏️"]
    [:button
     {:type :button
      :aria-label "Supprimer"
      :title "Supprimer"
      :on-click (fn [e] (on-delete-request belonging))}
     "🗑️"]
    [:button
     {:type "button"
      :aria-label "Monter"
      :title "Monter"
      :on-click
      (fn [e] (on-up belonging))}
     "↑"]
    [:button
     {:type "button"
      :aria-label "Descendre"
      :title "Descendre"
      :on-click
      (fn [e] (on-down belonging))}
     "↓"]]
   [:div.belonging_card
    [:div.belonging_card_content
     [:span.name name] [:br]
     [:span year] " - " [:span price] "€" [:br]]
    [:img {:src "https://placehold.it/64x64"}]]])


(defn belonging-form
  "Create a belonging form from an existing belonging"
  [belonging
   {:keys [on-edit-submit on-edit-cancel] :as handler}]
  (let [fields (reagent/atom belonging)]
    (fn []
      (let [{:keys [name year price]} @fields
            coerced (-> @fields
                        (update :year js/parseInt)
                        (update :price js/parseFloat))
            valid-form (spec/valid? ::belonging coerced)]
        [:div.belonging
         [:div.intents
          [:button
           {:type "button"
            :aria-label "Annuler"
            :title "Annuler"
            :on-click
            (fn [e] (on-edit-cancel coerced))}
           "✘"]
          [:button
           {:type :button
            :aria-label "Valider"
            :title "Valider"
            :disabled (not valid-form),
            :class (when-not valid-form ["opacity-50 cursor-not-allowed"]),
            :on-click (fn [e] (on-edit-submit coerced))}
           "✔"]]
         [:div.belonging_card
          [:form.belonging_card_content
           {:no-validate true}
           [:div.form-group
            {:class ["w-full sm:w-1/2"]
             :style {"--form-zoom" 1.2}}
            [:input.w-full
             {:name :name, :type :text, :value name
              :class (with-validity some? ::name name "")
              :on-change (form-field-changed! fields :name)}]
            [:label "Nom" [:span.hint (:name hints)]]
            [:span.bar]]
           [:div.form-group.inline-block.w-32
            {:style {"--form-zoom" 1}}
            [:input.w-full
             {:name :year, :value year
              :class (with-validity some? ::year (some-> year js/parseInt) "")
              :on-change (form-field-changed!
                          fields :year
                          incoming-int)}]
            [:label "Année" [:span.hint (:year hints)]]
            [:span.bar]]
           [:span " - "]
           [:div.form-group.inline-block.w-32
            {:style {"--form-zoom" 1}}
            [:input.w-full
             {:name :price, :value price
              :class (with-validity some? ::price (some-> price js/parseFloat) "")
              :on-change (form-field-changed! fields :price incoming-float 2)}]
            [:label "Prix" [:span.hint (:price hints)]]
            [:span.bar]]
           [:span "€"]]
          [:img {:src "https://placehold.it/64x64"}]]]))))


(defn belonging-view
  "Takes a belonging view-model and renders it depending on the view state"
  [{:keys [view belonging]} intents]
  (case (:mode view)
    :edition [belonging-form belonging intents]
    [belonging-card belonging intents]))

;;;;;;;;;;;;;;;;;;;;;;;
;; DATA MANIPULATION
;;;;;;;;;;;;;;;;;;;;;;;

(def empty-belonging
  "Value of an empty belonging with default values"
  {:name nil, :price nil, :year nil})

(def edition-belonging-vm ;; The -vm suffix means view-model
  "Value of a belonging view-model in an edition state"
  {:view {:mode :edition},
   :belonging empty-belonging})

(def fixtures
  "Fake data to test the layout"
  {:order [1 2]
   1 {:view {:mode :display},
      :belonging {:id 1
                  :name "Micro-ondes"
                  :year 2018
                  :price 45}}
   2 {:view {:mode :display},
      :belonging {:id 2
                  :name "Cafetière"
                  :year 2016
                  :price 25}}})

;; TODO Move this function in a utils lib
(defn indices
  "Returns the indices of items in collection that fulfill a predicate"
  [pred coll]
  (keep-indexed #(when (pred %2) %1) coll))

;; TODO Move this function in a utils lib
(defn index-of
  "Returns the first index of a value in a collection or nil if not found"
  [coll value]
  (loop [idx 0 items coll]
    (cond
      (empty? items) nil
      (= value (first items)) idx
      :else (recur (inc idx) (rest items)))))

;; TODO Move this function in a utils lib
(defn remove-by-index
  "Removes an item from a vector given its index, or do nothing (wrong index)"
  [vals idx]
  (if-not (and idx (<= 0 idx (dec (count vals))))
    vals
    (into (subvec vals 0 idx) (subvec vals (inc idx)))))

;; TODO Move this function in a utils lib
(defn remove-by-value
  "Removes an item from a vector given its value, or do nothing"
  [vals value]
  (remove-by-index vals (index-of vals value)))

;; TODO Move this function in a utils lib
(defn swap [vals idx1 idx2]
  "Swaps the places of two elements in a vector given their indices"
  (assoc vals
         idx1 (get vals idx2),
         idx2 (get vals idx1)))

;; TODO Move this function in a utils lib
(defn move-up-by-value
  "Moves the first encountered value up in a vector (swap with previous)"
  [vals value]
  (let [idx (index-of vals value)]
    (cond-> vals
      (> idx 0) (swap (dec idx) idx))))

;; TODO Move this function in a utils lib
(defn move-down-by-value
  "Moves the first encountered value down in a vector (swap with next)"
  [vals value]
  (let [idx (index-of vals value)]
    (cond-> vals
      (< idx (dec (count vals))) (swap idx (inc idx)))))

;;;;;;;;;;;;;;;;;;;;;;;;
;; ACTIONS
;;;;;;;;;;;;;;;;;;;;;;;;

(def actions
  {:start-edition
   (fn [belonging-vms belonging]
     (assoc-in belonging-vms
               [(:id belonging) :view :mode] :edition))
   :end-edition
   (fn [belonging-vms up-to-date]
     (let [id (:id up-to-date)]
       (-> belonging-vms
           (assoc-in [id :view :mode] :display),
           (assoc-in [id :belonging] up-to-date))))
   :cancel-edition
   (fn [belonging-vms being-edited]
     (let [id (:id being-edited)
           ;; old-belonging (get-in belonging-vms [id :belonging])
           ;; old-unindexed-value (dissoc old-belonging :id)
           ;; was-empty (= old-unindexed-value empty-belonging)
           ]
       (assoc-in belonging-vms [id :view :mode] :display)))
   :add
   (fn [belonging-vms belonging id]
     (-> belonging-vms
         (assoc id (assoc-in belonging [:belonging :id] id))
         (update :order conj id)))
   :delete
   (fn [belonging-vms belonging]
     (let [id (:id belonging)]
       (-> belonging-vms
           (dissoc id)
           (update :order remove-by-value id))))
   :move-up
   (fn [belonging-vms belonging]
     (update belonging-vms :order move-up-by-value (:id belonging)))
   :move-down
   (fn [belonging-vms belonging]
     (update belonging-vms :order move-down-by-value (:id belonging)))})

;;;;;;;;;;;;;;;;;;;;;;;;
;; STATE MANAGEMENT
;;;;;;;;;;;;;;;;;;;;;;;;

(defonce ^{:doc "Belonging key sequence counter"}
  counter (atom 10))

(defonce
  ^{:doc "State of the belonging-vm list"
    :private true}
  belongings
  (reagent/atom fixtures))


(def view-handler
  {:on-edit-request
   (partial swap! belongings (:start-edition actions))
   :on-edit-submit
   (partial swap! belongings (:end-edition actions))
   :on-edit-cancel
   (partial swap! belongings (:cancel-edition actions))
   :on-delete-request
   (partial swap! belongings (:delete actions))
   :on-up
   (partial swap! belongings (:move-up actions))
   :on-down
   (partial swap! belongings (:move-down actions))})



;;;;;;;;;;;;;;;;
;; PAGE VIEW
;;;;;;;;;;;;;;;;

(defn preview-page
  "HTML page for the preview"
  []
  [:div#preview
   [:span.back [:a {:href (path-for :index)} "Retour"]]
   [:h1 "Votre espace personnel"]
   [:div.tile
    [:h2 "Vos affaires"]
    [:div
     (let [sorted-belongings (map @belongings (:order @belongings))]
       (for [{:keys [view belonging] :as belonging-vm} sorted-belongings]
         ^{:key (:id belonging)}
         [belonging-view belonging-vm view-handler]))]
    [:div.actions
     [:button.plus
      {:on-click
       (fn [e]
         (swap! belongings (:add actions) edition-belonging-vm (swap! counter inc)))}]]]])
