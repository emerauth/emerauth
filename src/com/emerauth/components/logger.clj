(ns com.emerauth.components.logger
  (:require
   [com.stuartsierra.component :as component]
   [taoensso.timbre :as timbre]
   [taoensso.timbre.appenders.core :as timbre-ca]))

(defrecord Logger [config]
  component/Lifecycle
  (start [_]
    (let [level (or (get-in config [:logger :level])
                    [["*" :info]])]
      (timbre/set-level! level)
      (timbre/merge-config!
       {:appenders
        {:println (timbre-ca/println-appender
                   {:stream :auto})}})))
  (stop [_]))

(defn new-logger []
  (->Logger {}))
