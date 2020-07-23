#!/usr/bin/env runclj
^{:runclj {:npm [[express "4.17.1"]
                 [source-map-support "0.5.19"]]
           :lein [[org.clojure/clojure "1.10.1"]
                  [org.clojure/clojurescript "1.10.764"]]}}
(ns server)

(def port 8000)

(defn logging-middleware
  [req res next]
  (.on res "finish" #(println (.-statusCode res) (.-url req)))
  (next))

(defn status-handler
  [req res]
  (.send res "ok 200"))

(defn -main
  []
  (println "listening on:" port)
  (doto ((js/require "express"))
    (.use logging-middleware)
    (.get "/home" status-handler)
    (.listen port)))
