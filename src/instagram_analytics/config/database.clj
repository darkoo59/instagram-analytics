;; This namespace handles the configuration of the database datasource.
(ns instagram-analytics.config.database
  (:require
    [clojure.core :refer :all
     :exclude            [set update]]
    [hikari-cp.core :refer [make-datasource]]
    [environ.core :refer [env]]))

;; Defines the options for configuring the datasource, including connection properties
;; such as auto-commit, connection timeout, pool size...
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

;; Defines the datasource using HikariCP, which is a high-performance JDBC connection pool.
;; The datasource is lazily initialized using a delay to ensure it's only created when needed.
(defonce datasource
  (delay (make-datasource datasource-options)))