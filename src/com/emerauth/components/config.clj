(ns com.emerauth.components.config
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]
   [com.stuartsierra.component :as component]))

(defrecord Config [filename]
  component/Lifecycle
  (start [_]
    (-> (io/resource filename)
        aero/read-config))
  (stop [_]))

(defn new-config [filename]
  (->Config filename))
