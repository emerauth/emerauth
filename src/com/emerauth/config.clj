(ns com.emerauth.config
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]
   [malli.core :as m]))

(def LoggerSchema
  [:map
   [:level [:enum :info :warn :error]]
   [:appender [:enum :println]]])

(def ResourceSchema
  [:map-of string? map?])

(def AppSchema
  [:map
   [:host string?]
   [:port int?]
   [:uri string?]
   [:resources
    [:map-of keyword? ResourceSchema]]])

(def DBSchema
  [:map
   [:provider [:enum :postgres]]
   [:connection map?]])

(def AuthSchema
  [:map
   [:providers [:map-of keyword? map?]]
   [:jwt
    [:map
     [:alg [:enum :hs256]]
     [:ttl int?]]]])

(def RolesSchema
  [:map-of keyword? [:vector keyword?]])

(def ServerSchema
  [:map
   [:type [:enum :rest]]
   [:host string?]
   [:port int?]
   [:proxy-requests? boolean?]])

(def ConfigSchema
  [:map
   [:logger LoggerSchema]
   [:apps [:map-of keyword? AppSchema]]
   [:db DBSchema]
   [:auth AuthSchema]
   [:roles RolesSchema]
   [:server ServerSchema]])

(defn validate
  "Validates given CFG-CONTENT against ConfigSchema. When valid, returns
   CFG-CONTENT. Otherwise, throws an exception with an explanation."
  [cfg-content]
  (if-let [?explanation (m/explain ConfigSchema cfg-content)]
    (throw (ex-message {:cause :invalid-config
                        :value ?explanation}))
    cfg-content))

(defn parse
  "Reads, parse and validate given FILEPATH, which when
  not passed, defaults to the resource `config.example.edn`."
  ([]
   (-> (io/resource "config.example.edn")
       .getFile
       parse))
  ([filepath]
   (validate (aero/read-config filepath))))
