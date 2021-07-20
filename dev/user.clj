(ns user
  (:require
   [clojure.tools.namespace.repl :refer [refresh]]
   [datomic.client.api :as d]))

(def client (d/client {:server-type :dev-local
                       :system "dev"}))

(d/create-database client {:db-name "datomic-demo"})

(def conn (d/connect client {:db-name "datomic-demo"}))

(def schema
  [{:db/ident :event/name
    :db/valueType :db.type/string
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :event/timestamp
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one}])

(d/transact conn {:tx-data schema})

(d/transact conn {:tx-data [{:event/name "foo"
                             :event/timestamp 100}]})

(d/q [:find ?event
      :in $ ?a
      :where [?event ?a _]
      (d/db conn) :event/name])
