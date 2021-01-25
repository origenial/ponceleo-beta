(ns origenial.utils.react)

;; Source : https://github.com/thheller/code-splitting-clojurescript
(defmacro lazy-component [the-sym]
  `(origenial.utils.react/lazy-component* (shadow.lazy/loadable ~the-sym)))
