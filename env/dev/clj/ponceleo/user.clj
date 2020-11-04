(ns ponceleo.user
 (:require
  [clojure.java.io :as io]
  [ponceleo.handler :refer [loading-page]]
  [figwheel-sidecar.repl-api :as ra]))

(defn start-fw []
 (ra/start-figwheel!))

(defn stop-fw []
 (ra/stop-figwheel!))

(defn cljs []
 (ra/cljs-repl))

(defn create-index-html
 {:shadow.build/stage :flush}
 [build-state & folders]
 (let [content  (loading-page)
       out-dir  (apply io/file folders)
       out-file (io/file out-dir "index.html")]
  (io/make-parents out-file)
  (spit out-file content)
  (println "Written to " (.getPath out-file))
  build-state
  ))