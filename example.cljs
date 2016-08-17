(require-npm "express" "4.14.0")
(require-lein "prismatic/schema" "1.1.3")
(require [clojure.pprint :as pprint])

(def express (js/require "express"))
(def app (express))

(.use app (fn [req res next]
            (.on res "finish" #(println (.-statusCode res) (.-url req)))
            (next)))
(.get app "/status" (fn [req res]
                      (.send res "200 a ok!")))
(println "listen on 8000")
(.listen app 8000)
