#!/usr/bin/env runclj
^{:runclj {:npm [[express "4.16.3"]]
           :lein [[org.clojure/clojure "1.9.0"]
                  [org.clojure/clojurescript "1.10.339"]]}}
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
