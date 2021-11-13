#!/usr/bin/env runclj
^{:runclj {:npm [[express "4.17.1"]]
           :deps []}}
(ns server)

(def port 8000)

(defn logging-middleware
  [req res next]
  (.on res "finish" #(println (.-statusCode res) (.-url req)))
  (next))

(defn status-handler
  [req res]
  (.send res "ok 200"))

(defn main
  []
  (println "listening on:" port)
  (doto ((js/require "express"))
    (.use logging-middleware)
    (.get "/home" status-handler)
    (.listen port)))
