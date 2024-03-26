(ns unit.config-schema-test
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.test :as t]
   [com.emerauth.schemas.config :as s.cfg]
   [malli.core :as m]))

(t/testing "config schema validation"

  (t/deftest invalid-schema
    (let [cfg (-> (io/resource "config.example.edn")
                  slurp
                  edn/read-string)]
      [(t/is (m/validate s.cfg/Config cfg))]))

  (t/deftest invalid-schema
    [(t/is (not (m/validate s.cfg/Config {})))]))
