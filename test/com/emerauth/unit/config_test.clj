(ns com.emerauth.unit.config-test
  (:require
   [clojure.java.io :as io]
   [clojure.test :as t]
   [com.emerauth.config :as cfg]))

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

(t/testing "config parsing"

  (t/deftest valid-config
    (let [filepath (.getFile (io/resource "config.example.edn"))]
      (t/is example-config (cfg/parse filepath))))

  (t/deftest invalid-config
    (let [filename (str "config-" (random-uuid))
          tmpf (java.io.File/createTempFile filename ".edn")]
      (with-open [tmpf-out (io/writer tmpf)]
        (binding [*out* tmpf-out]
          (println "{:invalid :config}"))
        (t/is (thrown? Exception
                       (cfg/parse
                        (.getAbsolutePath tmpf)))))
      (.delete tmpf))))
