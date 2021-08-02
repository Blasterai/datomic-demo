(ns datomic.ion.demo.lambdas)
(:require
 [demo.db as db]
 [datomic.client.api :as d])

(defn all-events
  "Lambda ion that get all events"
  [_]
  (-> (db/all-events)))
