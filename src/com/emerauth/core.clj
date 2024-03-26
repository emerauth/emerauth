(ns com.emerauth.core
  (:require
   [clojure.java.io :as io]
   [com.emerauth.components.config :as config]
   [com.emerauth.components.db :as db]
   [com.emerauth.components.logger :as logger]
   [com.stuartsierra.component :as component]))

(defn build-system-map [config-path]
  (component/system-map
   :config (config/new-config config-path)
   :logger (component/using (logger/new-logger) [:config])
   :db (component/using (db/new-db) [:config])))

(defn -main
  "Emerauth's entry point.

   If no arg is passed, the default `config.example.edn` filpath will be used
   as for the config. When given (the first and only arg for now), the passed
   value will be used accordingly."
  ([]
   (-main (.getFile (io/resource "config.example.edn"))))
  ([& args]
   (let [system (component/start (build-system-map (first args)))]
     (.addShutdownHook (Runtime/getRuntime)
                       (component/stop system)))))
