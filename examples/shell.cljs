(ns main
  (:require [shell :as sh]
            [clojure.string :as s]
            [clojure.pprint :as pp]))

;; see the args
(println "args:" (.-argv js/process))

;; data munge some output
(println "")
(->> "ls /tmp | head"
  sh/run
  s/split-lines
  (map count)
  frequencies
  (sort-by first)
  (map println)
  dorun)

;; run a callback on each line of output
(println "")
(def num (atom 0))
(defn callback [line]
  (println "number:" (swap! num inc) "output:" line))
(sh/run-cb callback "ls" "/tmp" "|head")

;; inspect the results
(print "")
(prn (sh/run-cb identity "ls /tmp |head"))

;; deal with non zero exits
(print "")
(try
  (sh/run "false")
  (catch :default result
    (pp/pprint result)))
