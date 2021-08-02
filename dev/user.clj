(ns user
  (:require
   [clojure.tools.namespace.repl :refer [refresh]]
   [datomic.ion.demo.config :refer [prepare-config]]
   [datomic.client.api :as d]
   [datomic.ion.demo.db :as db]
   [muuntaja.core :as m]
   [reitit.core :as r]
   [reitit.ring :as ring]
   [ring.util.response :refer [response]]
   [reitit.ring.middleware.muuntaja
    :refer
    [format-negotiate-middleware format-request-middleware format-response-middleware]]
   [reitit.ring.middleware.parameters :refer [parameters-middleware]]
))

(def config (prepare-config))
