(ns instagram-analytics.services.user
  (:require
    [clojure.core]
    [clojure.java.jdbc
     :refer [with-db-connection query]]
    [instagram-analytics.config.database :refer [datasource]]
    [honey.sql :as sql]
    [honey.sql.helpers :refer [select from where ]]))

(defn uuid [] (java.util.UUID/randomUUID))

(defn is-user-verificated? [username password]
  (with-db-connection [conn {:datasource @datasource}]
                      (< 0
                         (count
                          (query conn
                                 (-> (select :*)
                                     (from :api_users)
                                     (where :and [:= :password password] [:= :username username])
                                     (sql/format)))))))

(defn is-username-verificated? [username password]
  (with-db-connection [conn {:datasource @datasource}]
                      (< 0
                         (count
                          (query conn
                                 (-> (select :*)
                                     (from :api_users)
                                     (where [:= :password password])
                                     (sql/format)))))))