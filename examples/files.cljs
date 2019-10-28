#!/usr/bin/env runclj
^{:runclj {:npm [[source-map-support "0.5.6"]]
           :lein [[org.clojure/clojure "1.10.0"]
                  [org.clojure/clojurescript "1.10.520"]]}}
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

(defn -main
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
