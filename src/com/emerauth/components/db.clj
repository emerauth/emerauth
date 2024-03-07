(ns com.emerauth.components.db
  (:require
    [xtdb.api :as xt]
    [com.stuartsierra.component :as component]))

(def specs 
  {:postgres 
   {:dialect-module 'xtdb.jdbc.psql/->dialect
    :tx-log-module 'xtdb.jdbc/->tx-log
    :document-store-module 'xtdb.jdbc/->document-store
    :connection-pool :xtdb.jdbc/connection-pool}})

(defrecord DB [config]
  component/Lifecycle
  (start [this]
    (let [provider (get-in config [:db :provider])
          spec (get specs provider)
          node-spec {(:connection-pool spec) 
                     {:dialect {:xtdb/module (:dialect-module spec)}
                      :db-spec (get-in config [:db :connection])}
                     :xtdb/tx-log 
                     {:xtdb/module (:tx-log-module spec)
                      :connection-pool (:connection-pool spec)}
                     :xtdb/document-store 
                     {:xtdb/module (:document-store-module spec)
                      :connection-pool (:connection-pool spec)}}
          node (xt/start-node node-spec)]
      (println "XTDB started")
      (assoc this :node node)))
  (stop [this]
    (.close (:node this))))

(defn new-db []
  (->DB {}))
