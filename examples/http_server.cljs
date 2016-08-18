(ns main
  #_(:npm [express "4.14.0"]))

(def port 8000)

(println "listening on:" port)

(def app ((js/require "express")))

(.get app "/" (fn [req res]
                (.send res "home page")))

(.get app "/status" (fn [req res]
                      (.send res "status is green")))

(.listen app port)
