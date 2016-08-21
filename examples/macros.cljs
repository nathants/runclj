(ns main
  #_(:lein [org.clojure/clojure "1.9.0-alpha10"]
           [org.clojure/clojurescript "1.9.216"])
  (:require-macros [main :refer [twice]]))

(defmacro twice
  [& forms]
  `(do ~@forms
       ~@forms))

(defn -main
  []
  (twice (println "hi!")))
