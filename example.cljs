(ns main
  #_(:npm [express "4.14.0"])
  #_(:lein [prismatic/schema "1.1.3"])
  (:require [clojure.pprint :as pprint]))

(def args (drop 2 (.-argv js/process)) )

(def cli-mode (not= 1 (count (.-argv js/process))))

(defn logging-middleware
  [req res next]
  (.on res "finish" #(println (.-statusCode res) (.-url req)))
  (next))

(defn status-handler
  [req res]
  (.send res "200 a ok!!!?????"))

(defn start-app [port]
  (println "listening on:" port)
  (doto ((js/require "express"))
    (.use #(apply #'logging-middleware %&))
    (.get "/status" #(apply #'status-handler %&))
    (.listen port)))

(when cli-mode
  (let [[port] args]
    (start-app (js/parseInt port))))
