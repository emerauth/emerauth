(ns com.emerauth.schemas.config
  "Spec for Emerauth's configuration file.")

(def Logger
  [:map
   [:level [:enum :info :warn :error]]
   [:appender [:enum :println]]])

(def Resource
  [:map-of string? map?])

(def App
  [:map
   [:host string?]
   [:port int?]
   [:uri string?]
   [:resources
    [:map-of keyword? Resource]]])

(def DB
  [:map
   [:provider [:enum :postgres]]
   [:connection map?]])

(def Auth
  [:map
   [:providers [:map-of keyword? map?]]
   [:jwt
    [:map
     [:alg [:enum :hs256]]
     [:ttl int?]]]])

(def Roles
  [:map-of keyword? [:vector keyword?]])

(def Server
  [:map
   [:type [:enum :rest]]
   [:host string?]
   [:port int?]
   [:proxy-requests? boolean?]])

(def Config
  [:map
   [:logger Logger]
   [:apps [:map-of keyword? App]]
   [:db DB]
   [:auth Auth]
   [:roles Roles]
   [:server Server]])
