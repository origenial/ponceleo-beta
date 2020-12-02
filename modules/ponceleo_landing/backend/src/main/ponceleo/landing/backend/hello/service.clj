(ns ponceleo.landing.backend.hello.service
 "This namespace defines basic core-logic functions.
  These functions should be the purest possible function to ensure testability.
  These function should not give any hint about the interface in which
  they'll be used."
  (:require
   [ponceleo.landing.backend.status :refer :all]))


(defn greeting-for
 "Core logic of the /greet endpoint"
 [nm]
 (let [dude (or (not-empty nm) "world")]
   (str "Hello, " dude "!\n")))



