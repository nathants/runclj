#!/usr/bin/env cljs
^{:runclj {:browser-mode true
           :lein [[org.clojure/clojure "1.9.0"]
                  [org.clojure/clojurescript "1.10.312"]
                  [garden "1.3.5"]
                  [funcool/bide "1.6.0"]
                  [reagent "0.8.2-SNAPSHOT"]]}}
(ns main
  (:require [reagent.core :as reagent]
            [bide.core :as bide]
            [garden.core :as garden]
            [clojure.browser.repl :as repl]))

(defonce state
  (reagent/atom
   {:page home
    :page1 {:state 0}}))

(def style
  (garden/css
   [:p {:font-size "16px"}]
   [:a {:margin "5px" :color "purple"}]))

(defn root []
  [:div
   [:style style]
   [:p
    [:a {:href "#/"} "home"]
    [:a {:href "#/page1"} "page 1"]
    [:a {:href "#/nothing-to-see/here"} "broken link"]]
   [:hr]
   [(:page @state)]])

(defn home []
  [:p "home page"])

(defn page1 []
  [:div
   [:p "this is a page with some data: " (-> @state :page1 :state)]
   [:p [:input {:type "button"
                :value "push me"
                :on-click #(swap! state update-in [:page1 :state] inc)}]]])

(defn not-found []
  [:p "404"])

(def router
  (bide/router
   [["/" home]
    ["/page1" page1]
    ["(.*)" not-found]]))

(defn run []
  ;; (repl/connect "http://localhost:9000/repl")
  (bide/start! router {:default home :on-navigate #(swap! state assoc :page %) :html5? false})
  (reagent/render [root] (js/document.getElementById "app")))
