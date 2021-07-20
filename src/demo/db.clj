(ns demo.db
  (:require
   [clojure.tools.namespace.repl :refer [refresh]]
   [cprop.core :refer [load-config cursor]]
   [datomic.client.api :as d]))

(def config (load-config))
(def env (:environment config))
(def db-config (:db config))

(def client (d/client (get-in db-config [:connection env])))

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

(def initial-data
  [{:event/name "boo"
    :event/timestamp 100}
   {:event/name "bar"
    :event/timestamp 200}])

(d/transact conn {:tx-data schema})
(d/transact conn {:tx-data initial-data})

(defn save-event [{:keys [name ts]}]
  (d/transact conn {:tx-data {:event/name name
                              :event/timestamp ts}}))

(defn all-events []
  (flatten (d/q '[:find (pull ?e [*])
                :where [?e :event/name _]]
              (d/db conn))))

(comment
  (all-events)
  (d/q '[:find (pull ?e [*])
         :where [?e :event/name _]]
       (d/db conn))
  (d/pull  (d/db conn) '[* :event/name]))
