(ns demo.server
  (:gen-class)
  (:require
   [clojure.tools.logging :as log]
   [muuntaja.core :as m]
   [reitit.core :as r]
   [reitit.ring :as ring]
   [ring.util.response :refer [response]]
   [reitit.ring.middleware.muuntaja
    :refer
    [format-negotiate-middleware format-request-middleware format-response-middleware]]
   [reitit.ring.middleware.parameters :refer [parameters-middleware]]
   [demo.db :as db]
   ))

(def router
  (ring/router
   [""
    ["/status" {:name ::ping
                :get (fn [_] (response {:status "ok"}))}]
    ["/db/all-events" {:get db/all-events}]
    ]
   {:data {:muuntaja m/instance
           :middleware [format-negotiate-middleware
                        format-response-middleware
                        parameters-middleware
                        format-request-middleware]}}))

(def app
  (ring/ring-handler
   router))

(comment
  (r/match-by-path router "/status")
  (r/match-by-path router "/db/all-events"))
