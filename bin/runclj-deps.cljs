#!/usr/bin/env runclj
^{:runclj {:lein [[org.clojure/clojure "1.10.0"]
                  [org.clojure/clojurescript "1.10.439"]
                  [org.clojure/tools.reader "1.3.2"]]}}
(ns runclj-deps
  (:require [clojure.string :as s]
            [cljs.tools.reader :as r :refer [*data-readers* *default-data-reader-fn* read-string *alias-map* resolve-symbol]]
            [cljs.tools.reader.impl.utils :refer [reader-conditional reader-conditional?]]
            [cljs.tools.reader.reader-types :as rt]
            [goog.string]))

(defn slurp
  [path]
  (.readFileSync (js/require "fs") path "utf-8"))

(defn -main
  [filepath key]
  (binding [*default-data-reader-fn* (fn [a b] b)
            resolve-symbol identity]
    (let [key (r/read-string key)
          text (slurp filepath)
          lines (s/split-lines text)
          lines (if (s/starts-with? (first lines) "#!") (rest lines) lines)
          text (s/join "\n" lines)
          reader (rt/source-logging-push-back-reader text)
          forms (->> #(r/read {:eof :runclj-deps-stop} reader)
                  repeatedly
                  (take-while #(not= % :runclj-deps-stop)))]
      (cond
        (#{:npm :lein} key) (->> forms
                              (filter #(= 'ns (first %)))
                              first
                              meta
                              :runclj
                              key
                              (map pr)
                              dorun)
        (= :require key) (->> forms
                           (filter #(= 'ns (first %)))
                           first
                           (filter seq?)
                           (filter #(= key (first %)))
                           first
                           rest
                           (map pr)
                           dorun)
        (= "mode" (second (s/split (name key) "-"))) (->> forms
                                                       (filter #(= 'ns (first %)))
                                                       first
                                                       meta
                                                       :runclj
                                                       key
                                                       (get {true 0 false 1 nil 1})
                                                       js/process.exit)
        (= :source key) (as-> forms $
                          (map #(assoc (meta %) :term (first %)) $)
                          (map #(dissoc % :runclj) $)
                          (map #(if (= 'defmacro (:term %))
                                  (update-in % [:source]
                                             (fn [x]
                                               (s/join "\n"
                                                   (map (fn [x]
                                                          (str ";; " x))
                                                        (s/split-lines x)))))
                                  %) $)
                          (map :source $)
                          (map #(println % "\n") $)
                          (dorun $))
        (= :macro key) (do (->> forms (filter #(= 'ns (first %))) first (take 2) println)
                           (println)
                           (as-> forms $
                             (map #(assoc (meta %) :term (first %)) $)
                             (map #(dissoc % :runclj) $)
                             (filter #(= 'defmacro (:term %)) $)
                             (map :source $)
                             (map #(println % "\n") $)
                             (dorun $)))
        (= :ns key) (->> forms (filter #(= 'ns (first %))) first second println)
        :else (println "unkown cmd:" key)))))
