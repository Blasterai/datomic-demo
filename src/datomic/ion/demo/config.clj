(ns datomic.ion.demo.config
  (:require
   [cprop.core :refer [load-config cursor]]
   [clojure.walk :as walk]
   [clojure.edn :as edn]
   [datomic.ion :as ion]))

(defn prod? [config]
  (= (:env config) :prod))

(defn map-vals
  "Maps f to all vals in m"
  [m f]
  (into {} (for [[k v] m] [k (f v)])))

(defn get-prod-params! []
  (-> {:path "/datomic-shared/prod/demo-project/db-conn/"}
      (ion/get-params)
      (walk/keywordize-keys)
      (map-vals edn/read-string)
      ))

(defn prepare-config []
  (let [conf (load-config)]
    (if (prod? conf)
      (assoc-in conf [:db :connection :prod] (:db-conn (get-prod-params!)))
      conf)))

(comment
  (prod? config)
  (ion/get-params {:path "/datomic-shared/prod/clj-ynab/"})
  (get-prod-params!)
  (prepare-config)
 )
