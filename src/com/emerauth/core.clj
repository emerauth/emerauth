(ns com.emerauth.core
  (:require
   [com.emerauth.components.config :as config]
   [com.emerauth.components.logger :as logger]
   [com.stuartsierra.component :as component]))

(def system-map
  (component/system-map
   :config (config/new-config "config.prod.edn")
   :logger (component/using (logger/new-logger) [:config])))

(defn -main
  "Emerauth's entry point."
  ([]
   (-main {}))
  ([& _args]
   (let [system (component/start system-map)]
     (.addShutdownHook (Runtime/getRuntime)
                       (component/stop system)))))
