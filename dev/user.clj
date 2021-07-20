(ns user
  (:require
   [clojure.tools.namespace.repl :refer [refresh]]
   [cprop.core :refer [load-config cursor]]
   [datomic.client.api :as d]))

(def config (load-config))
