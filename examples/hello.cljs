#!/usr/bin/env cljs
(ns main
  #_ (:npm [source-map-support "0.5.6"])
  #_ (:lein [org.clojure/clojure "1.9.0"]
            [org.clojure/clojurescript "1.10.312"]))

(defn -main
  [& args]
  (println :hello :world args))
