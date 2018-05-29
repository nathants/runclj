(ns main
  #_(:lein [org.clojure/clojure "1.9.0"]
           [org.clojure/clojurescript "1.10.238"])
  (:require-macros [main :refer [twice]]))

(defmacro twice
  [& forms]
  `(do ~@forms
       ~@forms))

(defn -main
  []
  (twice (println "hi!")))
