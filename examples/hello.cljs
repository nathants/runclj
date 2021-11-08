#!/usr/bin/env runclj
^{:runclj {:npm []
           :deps []}}

(ns hello)

(defn main
  [& args]
  (throw "asdf")
  (println :hello :world args))
