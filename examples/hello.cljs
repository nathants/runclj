#!/usr/bin/env runclj
^{:runclj {:npm [[source-map-support "0.5.19"]]
           :lein [[org.clojure/clojure "1.10.1"]
                  [org.clojure/clojurescript "1.10.764"]]}}
(ns hello)

(defn -main
  [& args]
  (println :hello :world args))
