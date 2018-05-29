(ns main
  #_ (:lein [org.clojure/clojure "1.9.0"]
            [org.clojure/clojurescript "1.10.238"])
  (:require [clojure.string :as s]
            [clojure.pprint :as pp]
            [shell :as sh]))

(defn -main
  [& args]
  (println (sh/run "echo cli args:" (vec args)))

  (println "\n;; data munge some output")
  (->> "ls /tmp | head"
    sh/run
    s/split-lines
    (map count)
    frequencies
    (sort-by first)
    (map println)
    dorun)

  (println "\n;; run a callback on each line of output")
  (def num (atom 0))
  (defn callback [line]
    (println "number:" (swap! num inc) "output:" line))
  (sh/run callback "ls" "/tmp" "|head")

  (print "\n;; deal with non zero exits")
  (try
    (sh/run "false")
    (catch :default result
      (pp/pprint result))))
