#!/usr/bin/env runclj
^{:runclj {:npm []
           :deps []}}
(ns main
  (:require [clojure.string :as s]))

(defn slurp
  [path]
  (.readFileSync (js/require "fs") path "utf-8"))

(defn spit
  [path text]
  (.writeFileSync (js/require "fs") path text))

(defn loads [x]
  (js->clj (js/JSON.parse x) :keywordize-keys true))

(defn dumps [x]
  (js/JSON.stringify (clj->js x)))

(defn main
  [& args]
  (let [in-path "files.json"
        out-path (str in-path ".output")]
    (->> in-path
      slurp
      s/split-lines
      (map loads)
      (map #(mapv % [:start :end]))
      (map #(concat ["start:end"] %))
      (map dumps)
      (s/join "\n")
      (spit out-path))
    (println "info:" in-path "->" out-path)))
