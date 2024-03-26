(ns com.emerauth.schemas.config
  "Spec for Emerauth's configuration file."
  (:require
   [malli.core :as m]))

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

(comment
  (def example-config
    {:logger {:level :info
              :appender :println}
     :apps {:frontend {:host "localhost"
                       :port 8080
                       :uri "/"
                       :resources
                       {:public
                        {"/auth" {:methods ["post"]}
                         "/home" {:methods ["get"]}}
                        :protected
                        {"/auth" {:methods ["put"]}
                         "/store" {:methods ["get"]}}
                        :marketplace
                        {"/marketplace" {:methods ["get"]}
                         "/marketplace/:product"
                         {:methods ["get" "post" "put" "delete"]}}}}
            :backend {:host "localhost"
                      :port 9000
                      :uri "/api"
                      :resources
                      {:public {"HealthCheck" {}
                                "SignUp" {}
                                "SignIn" {}}
                       :protected {"RefreshToken" {}}
                       :store {"GetProducts" {}}
                       :marketplace {"UpdateProduct" {}
                                     "DeleteProduct" {}}}}}
     :db {:provider :postgres
          :connection {:host "localhost"
                       :port 5432
                       :dbname "emerauth"
                       :user "emer-user"
                       :password "emer-pass"}}
     :auth {:providers {:otp {}}
            :jwt {:alg :hs256
                  :ttl 3600}}
     :roles {:customer [:frontend/store]
             :seller [:frontend/store :backend/store]}
     :server {:type :rest
              :host "0.0.0.0"
              :port 4078
              :proxy-requests? false}})

  (m/validate Config example-config)
  ;;
  )
