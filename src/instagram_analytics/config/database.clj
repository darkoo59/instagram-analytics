(ns instagram-analytics.config.database
  (:require
    [clojure.core :refer :all
     :exclude            [set update]]
    [hikari-cp.core :refer [make-datasource]]
    [environ.core :refer [env]]))

(def datasource-options
  {:auto-commit        true
   :read-only          false
   :connection-timeout 30000
   :validation-timeout 5000
   :idle-timeout       600000
   :max-lifetime       1800000
   :minimum-idle       10
   :maximum-pool-size  20
   :pool-name          "db-pool"
   :adapter            "postgresql"
   :username           "postgres"
   :password           "admin"
   :database-name      "instagram-analytics-dev"
   :server-name        "localhost"
   :port-number        "5432"
   :register-mbeans    false})

(defonce datasource
  (delay (make-datasource datasource-options)))