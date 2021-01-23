(ns ponceleo.landing.frontend.pages.preview.index
  (:require
   [clojure.spec.alpha :as spec]
   [origenial.utils.form :refer [form-field-changed! with-validity
                                 incoming-int incoming-float]]
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
   {:keys [on-edit-request on-delete-request on-up on-down] :as intents}]
  [:div.belonging.belonging-card
   [:div.info
    [:span.name name] [:br]
    [:span year] " - " [:span price] "€" [:br]
    [:div.intents
     [:button
      {:type "button"
       :on-click
       (fn [e] (on-edit-request belonging))}
      "Editer"]
     [:button
      {:type :button
       :on-click (fn [e] (on-delete-request belonging))}
      "Supprimer"]]]
   [:img {:src "https://placehold.it/64x64"}]])


(defn belonging-form
  "Create a belonging form from an existing belonging"
  [belonging
   {:keys [on-edit-submit on-edit-cancel] :as intents}]
  (let [fields (reagent/atom belonging)]
    (fn []
      (let [{:keys [name year price]} @fields
            coerced (-> @fields
                        (update :year js/parseInt)
                        (update :price js/parseFloat))
            valid-form (spec/valid? ::belonging coerced)]
        [:div.belonging.belonging-card
         [:form.info.belonging-form
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
          [:span "€"]
          [:div.intents
           [:button
            {:type "button"
             :on-click
             (fn [e] (on-edit-cancel coerced))}
            "Annuler"]
           [:button
            {:type :button
             :disabled (not valid-form),
             :class (when-not valid-form ["opacity-50 cursor-not-allowed"]),
             :on-click (fn [e] (on-edit-submit coerced))}
            "Valider"]]]
         [:img {:src "https://placehold.it/64x64"}]]))))


(defn belonging-view
  "Takes a belonging view-model and renders it depending on the view state"
  [{:keys [view belonging]} intents]
  (case view
    :editing [belonging-form belonging intents]
    [belonging-card belonging intents]))

;;;;;;;;;;;;;;;;;;;;;;;
;; DATA MANIPULATION
;;;;;;;;;;;;;;;;;;;;;;;

(def empty-belonging
  "Value of an empty belonging with default values"
  {:name nil, :price nil, :year nil})

(def editing-belonging-vm ;; The -vm suffix means view-model
  "Value of a belonging view-model in an editing state"
  {:view :editing,
   :belonging empty-belonging})

(def fixtures
  "Fake data to test the layout"
  {1 {:view :ok,
      :belonging {:name "Micro-ondes"
                  :year 2018
                  :price 45}}
   2 {:view :ok,
      :belonging {:name "Cafetière"
                  :year 2016
                  :price 25}}})

;;;;;;;;;;;;;
;; PAGE
;;;;;;;;;;;;;

(defonce ^{:doc "Belonging key sequence counter"}
  counter (atom 10))

(defonce
  ^{:doc "State of the belonging-vm list"
    :private true}
  belongings
  (reagent/atom {} #_fixtures))

(defn preview-page
  "HTML page for the preview"
  []
  [:div#preview
   [:span.back [:a {:href (path-for :index)} "Retour"]]
   [:h1 "Votre espace personnel"]
   [:div.tile
    [:h2 "Vos affaires"]
    [:div
     (for [[k belonging-vm] @belongings]
       ^{:key k}
       [belonging-view belonging-vm
        {:on-edit-request
         (fn [_] (swap! belongings assoc-in [k :view] :editing))
         :on-edit-submit
         (fn [up-to-date] (swap! belongings assoc k {:view :ok,
                                                    :belonging up-to-date}))
         :on-edit-cancel
         (fn [_] (swap! belongings dissoc k))
         :on-delete-request
         (fn [_] (swap! belongings dissoc k))}])]
    [:div.actions
     [:button.plus
      {:on-click (fn [e]
                   (swap! counter inc)
                   (swap! belongings assoc @counter editing-belonging-vm))}]]]])
