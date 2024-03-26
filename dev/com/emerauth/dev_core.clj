(ns com.emerauth.dev-core
  (:require
   [clojure.java.io :as io]
   [com.emerauth.components.config :as config]
   [com.emerauth.components.logger :as logger]
   [com.emerauth.components.db :as db]
   [com.stuartsierra.component :as component]))

(def system-map
  (component/system-map
   :config (config/new-config (.getFile (io/resource "config.example.edn")))
   :logger (component/using (logger/new-logger) [:config])
   :db (component/using (db/new-db) [:config])))

(comment
  (def system (atom nil))
  (reset! system (component/start system-map))
  (component/stop system)
  ;;
  )
