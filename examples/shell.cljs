#!/usr/bin/env runclj
^{:runclj {:npm [[prompt "1.3.0"]]
           :deps []}}

(ns shell
  (:require [clojure.string :as s]
            [clojure.pprint :as pp]
            [cljs.core.async :as a :refer [go >! <! put! take! chan]]))

(defn run
  [& cmd]
  (let [[cb cmd] (if (fn? (first cmd))
                   [(first cmd) (rest cmd)]
                   [identity cmd])
        cmd (s/join " " (map str cmd))
        data (volatile! "")
        chan (chan)]
    (doto (.spawn (js/require "child_process") "bash" #js ["-c" cmd])
      (-> .-stdout (.on "data" #(let [text (.toString % "utf8")]
                                  (dorun (map cb (s/split-lines text)))
                                  (vswap! data str text))))
      (-> .-stderr (.on "data" #(println (.toString % "utf8"))))
      (-> (.on "close" (fn [exit-code]
                         (put! chan {:exit exit-code :output @data :cmd cmd :cwd (.cwd js/process)})))))
    chan))

(defn prompt
  [text]
  (let [p (js/require "prompt")
        chan (chan)]
    (.start p)
    (.get p text (fn [err res]
                   (put! chan (first (vals (js->clj res))))))
    chan))

(defn main
  [& args]
  (go
    (pr (<! (run println "echo 1; echo 2; echo args:" args)))
    (println "hi:" (<! (prompt "whats your name? ")))))
