(ns com.emerauth.components.config
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]
   [com.emerauth.schemas.config :as s.cfg]
   [com.stuartsierra.component :as component]
   [malli.core :as m]))

(defrecord Config [filepath]
  component/Lifecycle
  (start [_]
    (let [cfg (-> (io/file filepath)
                  aero/read-config)
          ?explanation (m/explain s.cfg/Config cfg)]
      (if ?explanation
        (throw (ex-message {:cause :invalid-config
                            :explanation ?explanation}))
        cfg)))
  (stop [_]))

(defn new-config [filepath]
  (->Config filepath))
