(ns main
  (:require [shell :as sh]
            [clojure.string :as s]))

(println "")

(->> "whoami"
  sh/run
  :output
  s/upper-case
  (str "i am: ")
  println)

(println "")

(->> "ls /tmp | head"
  sh/run
  :output
  s/split-lines
  (map count)
  frequencies
  (map println)
  dorun)
