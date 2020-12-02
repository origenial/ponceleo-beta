(ns ponceleo.landing.frontend.next.pages.main
  "This namespace gathers utilities, classes and methods to transform the
  SPA's reitit router into a Next.js file based router consisting of a
  `pages` directory and `**/*.js` files exporting pages (React components)
  and next.js static methods (Static Site Generation and Server Side Rendering
  helpers)"
  (:require
   [clojure.string :refer [split]]
   [goog.object :as gobj]
   [ponceleo.landing.frontend.core :refer [page-container]]
   [ponceleo.landing.frontend.router :refer [router]]
   [reitit.core :as reitit]))

#_(defn next-getStaticPaths
  "Custom getStaticPaths for dynamic routing.
   Uses SPA's reitit router to define page paths"
  {:export true
   :next/page ["[...slug].js"]
   :next/export-as "getStaticPaths"}
  []
  (let [compiled-routes ;; [["/path1" {:name :name-1} opts] ... ]
        (->> router
             reitit/compiled-routes
             (remove (comp #{:index :error-404} :name second))) ;; Filter index

        slug<-route ;;Create a slug vector given a route
        #(-> % first (subs 1) (split #"/"))

        path<-slug ;;Create a path js object given a slug-vector
        #(do #js{:params #js{:slug (apply array %)}})

        path<-route
        (comp path<-slug slug<-route)

        paths ;;Extract paths from compiled routes
        (apply array (mapv path<-route compiled-routes))]
    #js {:paths paths, :fallback false}))

#_(defn next-getStaticProps
  "Custom getStaticProps for dynamic routing.
   Uses SPA's reitit router and path params to define page props"
  {:export true
   :next/page        ["[...slug].js"]
   :next/export-as   "getStaticProps"}
  [path]
  (let [slug (gobj/getValueByKeys path "params" "slug")
        path (str "/" (interpose "/" slug))
        match (reitit/match-by-path router path)
        page-name (get-in match [:data :name])]
     #js{:props #js{:pageName page-name}}))

#_(defn next-page
  "Defines a dynamically resolved next.js page"
  {:export true
   :next/page        ["[...slug].js"]
   :next/export-as   "default"}
  [props]
  (let [page-name (gobj/get props "pageName")]
        (page-container page-name)))

#_(defn next-index-page
  "Defines the Next.js index.js page"
  {:export true
   :next/page        ["index.js"]
   :next/export-as   "default"}
  [_]
  (page-container :index))

