#!/usr/bin/env runclj
^{:runclj {:npm [[express "4.17.1"]
                 [ws "8.2.3"]]
           :deps []}}

(ns server)

(def express (js/require "express"))

(defonce server-ref
  (atom nil))

(def port 8000)

(defn logging-middleware
  [req res next]
  (.on res "finish" #(println (.-statusCode res) (.-url req)))
  (next))

(defn router [server]
  (-> server
    (.get "/" (fn [req res]
                (.send res "ok!")))
    (.get "/nope" (fn [req res]
                    (.send res "nope")))))

(defn start []
  (let [server (express)]
    (.use server logging-middleware)
    (router server)
    (.listen server port)
    (reset! server-ref server)))

(defn ^:dev/after-load main []
  (println "listening on:" port)
  (if-let [server @server-ref]
    (.close server (fn [err] (start)))
    (start)))

;; #!/usr/bin/env runclj
;; ^{:runclj {:npm [[express "4.17.1"]]
;;            :deps []}}
;; (ns server)

;; (def port 8000)

;; (defn logging-middleware
;;   [req res next]
;;   (.on res "finish" #(println (.-statusCode res) (.-url req)))
;;   (next))

;; (defn status-handler
;;   [req res]
;;   (.send res "ok 200"))

;; (defn main
;;   []
;;   (println "listening on:" port)
;;   (doto ((js/require "express"))
;;     (.use logging-middleware)
;;     (.get "/home" status-handler)
;;     (.listen port)))
