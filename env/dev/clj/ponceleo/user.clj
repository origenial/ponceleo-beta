(ns ponceleo.user
 (:require
  [clojure.java.io :as io]
  [ponceleo.handler :refer [loading-page]]))

(defn create-index-html [& folders]
 (let [content  (loading-page)
       out-dir  (apply io/file folders)
       out-file (io/file out-dir "index.html")]
  (io/make-parents out-file)
  (spit out-file content)
  (println "Written to " (.getPath out-file))))

