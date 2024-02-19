(ns instagram-analytics.config.database
  (:require
    [clojure.core :refer :all
     :exclude            [set update]]
    [hikari-cp.core :refer [make-datasource]]
    [environ.core :refer [env]]))

(def datasource-options
  {:auto-commit        true
   :read-only          false
   :managed-connection 30000
   :validation-timeout 5000
   :idle-timeout       600000
   :max-lifetime       1800000
   :minimum-idle       10
   :maximum-pool-size  10
   :pool-name          "db-pool"
   :adapter            "postgresql"
   :username           (env :database-username)
   :password           (env :database-password)
   :database-name      (env :database-name)
   :server-name        (env :database-server)
   :port-number        (env :database-port)
   :register-mbeans    false})

(defonce datasource
  (delay (make-datasource datasource-options)))