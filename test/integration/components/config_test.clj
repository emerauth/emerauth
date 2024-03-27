(ns integration.components.config-test
  (:require
   [clojure.java.io :as io]
   [clojure.test :as t]
   [com.emerauth.components.config :as config]
   [com.stuartsierra.component :as component]))

(t/testing "config component"

  (t/deftest valid-config
    (let [filepath (.getFile (io/resource "config.example.edn"))
          *config-cmp* (atom nil)]
      (t/is (reset! *config-cmp*
                    (component/start
                     (config/new-config filepath))))
      (component/stop @*config-cmp*)))

  (t/deftest invalid-config
    (let [filename (str "config-" (random-uuid))
          tmpf (java.io.File/createTempFile filename ".edn")]
      (with-open [tmpf-out (io/writer tmpf)]
        (binding [*out* tmpf-out]
          (println "{:invalid :config}"))
        (t/is (thrown? Exception
                       (component/start
                        (config/new-config (.getAbsolutePath tmpf))))))
      (.delete tmpf))))
