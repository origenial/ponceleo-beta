(ns origenial.utils.lorem-ipsum
  "Source : https://gist.github.com/noprompt/5400558
   Base utilities for generating lorem-ipsums words, sentences, paragraphs
  "
  (:require [clojure.string :as string]))

(def lorem-ipsum
  "Lorem ipsum paragraph"
  "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium
   doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo
   inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.
   Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut
   fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem
   sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit
   amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora
   incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad
   minima veniam, quis nostrum exercitationem ullam corporis suscipit
   laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum
   iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae
   consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?")

(def sentences
  "Returns lorem ipsum sentences (vector)"
  (string/split lorem-ipsum #"(.*[.?]) "))

(defn random-sentence
  "Returns one or many lorem ipsum random sentences (string)"
  ([] (random-sentence 1))
  ([n] (take n (repeatedly #(nth sentences (rand-int (count sentences)))))))

(defn random-word
  "Returns one or many lorem ipsum words"
  ([] (random-word 1))
  ([n] (take n (string/split (first (random-sentence)) #"[, ]"))))

(defn random-paragraph
  "Returns one or many lorem ipsum parapgraph "
  ([] (random-paragraph 1))
  ([n] (take n (repeatedly #(string/join \space (random-sentence 5))))))
