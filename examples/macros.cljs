#!/usr/bin/env runclj
^{:runclj {:lein [[org.clojure/clojure "1.9.0"]
                  [org.clojure/clojurescript "1.10.339"]]}}
(ns macros
  (:require-macros [macros :refer [twice]]))

(defmacro twice
  [& forms]
  `(do ~@forms
       ~@forms))

(defn -main
  []
  (twice (println "hi!")))
