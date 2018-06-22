#!/usr/bin/env cljs
^{:runclj {:lein [[org.clojure/clojure "1.9.0"]
                  [org.clojure/clojurescript "1.10.238"]]}}
(ns main
  (:require-macros [main :refer [twice]]))

(defmacro twice
  [& forms]
  `(do ~@forms
       ~@forms))

(defn -main
  []
  (twice (println "hi!")))
