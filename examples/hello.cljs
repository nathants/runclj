#!/usr/bin/env runclj
^{:runclj {:npm [[source-map-support "0.5.6"]]
           :lein [[org.clojure/clojure "1.10.0"]
                  [org.clojure/clojurescript "1.10.439"]]}}
(ns hello)

(defn -main
  [& args]
  (println :hello :world args))
