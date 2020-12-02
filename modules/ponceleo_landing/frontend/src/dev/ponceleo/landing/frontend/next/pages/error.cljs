(ns ponceleo.landing.frontend.next.pages.error
  "This namespace gathers utilities, classes and methods to export the
  SPA's error pages to Next.js file based router. See `ponceleo.next.main`for
  more explanations"
  (:require [ponceleo.landing.frontend.core :refer [page-container]]))

(defn next-error-page
  "Custom Nextjs 404 error page"
  {:export true
   :next/page        ["404.js"]
   :next/export-as   "default"}
  [_]
  (page-container :error-404))