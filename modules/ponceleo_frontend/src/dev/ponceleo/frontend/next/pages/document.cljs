(ns ponceleo.frontend.next.pages.document
  "This namespace gathers utilities, classes and methods to create Next.js'
   pages/_document.js (Tuning of outer body HTML).
   See Next.js 'Custom Document' for more explanations"
  (:require
   ["next/document" :default Document :refer [Html, Head, Main, NextScript]]
   [goog.object :as gobj]
   [origenial.utils :refer-macros [extend-js-class]]
   [reagent.core :as r]))


(defn next-document-render
  "Custom Next.js Document.renderDocument(ctx) static method"
  []
  (r/as-element
    [:> Html
     [:> Head]
     [:body
      [:> Main]
      [:> NextScript]]]))

(defn next-document-getInitialProps
  "Custom Next.js Document.getInitialProps(ctx) static method"
  [ctx]
  (-> (. Document getInitialProps ctx)
      (.then #(gobj/clone %))))


;; Make document extend from "next/document"'s Document
(declare next-document)
(extend-js-class ^{:export         true
                   :next/page      ["_document.js"]
                   :next/export-as "default"
                   :jsdoc          ["@constructor"]}
                 next-document Document
                 {:render next-document-render}
                 false
                 {:renderDocument  (. Document -renderDocument)
                  :getInitialProps next-document-getInitialProps})

