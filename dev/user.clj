(ns user
  (:require
   [clojure.tools.namespace.repl :refer [refresh]]
   [demo.config :refer [prepare-config]]
   [datomic.client.api :as d]))

(def config (prepare-config))
