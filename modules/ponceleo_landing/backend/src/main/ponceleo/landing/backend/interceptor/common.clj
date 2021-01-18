(ns ponceleo.landing.backend.interceptor.common
  (:require
   [clojure.data.json :as json]
   [io.pedestal.http.content-negotiation :as conneg]))

;;;;;;;;;;;;;;
;; Utilities

(def content-transformations
  "Defines a map of supported content-types to content-transformations.
   The first supported Content-Type will be the default Content-Type."
  {"text/plain" identity
   "text/html" identity
   "application/edn" pr-str
   "application/json" json/write-str})

(def supported-content-types
 "List of supported content-types"
  (keys content-transformations))

(defn accepted-type
  "Gets the content-type to use from the HTTP request"
  [context]
  (get-in context [:request :accept :field] (first supported-content-types)))

(defn transform-content
  "Convert a content in the requested content-type."
  [body content-type]
  ((content-transformations content-type) body))

(defn coerce-to
  "Coerce an HTTP response to the requested content-type"
  [response content-type]
  (-> response
    (update :body transform-content content-type)
    (assoc-in [:headers "Content-Type"] content-type)))

;;;;;;;;;;;;;;
;; Interceptors

(def content-negotiator
 "Defines a content-negociation Interceptor"
 (conneg/negotiate-content supported-content-types))

(def body-coercer
 "Interceptor that coerce the HTTP response to the Accepted Content-Type if
 not already done yet.
 Only supports `ponceleo.landing.backend.interceptor.common/supported-content-types`"
  {:name ::body-coercer
   :leave
   (fn [context]
     (cond-> context
       ;If a Content-Type is already provided, return the context as is
       ;Else coerce the response to the Accepted content-type
       (nil? (get-in context [:response :headers "Content-Type"]))
       (update :response coerce-to (accepted-type context))))})