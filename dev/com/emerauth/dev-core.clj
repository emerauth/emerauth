(ns com.emerauth.dev-core
  (:require
   [com.emerauth.components.config :as config]
   [com.emerauth.components.logger :as logger]
   [com.stuartsierra.component :as component]))

(def system-map
  (component/system-map
   :config (config/new-config "config.dev.edn")
   :logger (component/using (logger/new-logger) [:config])))

(comment
  (def system (atom nil))
  (reset! system (component/start system-map))
  (component/stop system)
  ;;
  )
