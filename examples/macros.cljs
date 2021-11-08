#!/usr/bin/env runclj
^{:runclj {:deps []}}

(ns macros
  (:require-macros [macros :refer [twice]]))

(defmacro twice
  [& forms]
  `(do ~@forms
       ~@forms))

(defn main
  []
  (twice (println "hi!")))
