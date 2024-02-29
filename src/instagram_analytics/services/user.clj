(ns instagram-analytics.services.user
  (:require
    [clojure.core]
    [clojure.java.jdbc
     :refer [with-db-connection query execute!]]
    [instagram-analytics.config.database :refer [datasource datasource-options]]
    [honey.sql :as sql]
    [honey.sql.helpers :refer [select from where insert-into columns values]]))

(defn uuid [] (java.util.UUID/randomUUID))

(defn is-user-verificated? [username password]
  (with-db-connection [conn {:datasource @datasource}]
                      (< 0
                         (count
                          (query conn
                                 (-> (select :*)
                                     (from :api_users)
                                     (where :and [:= :password password] [:= :username username])
                                     (sql/format)))
                          )
                         )))

(defn is-username-verificated? [username]
  (with-db-connection [conn {:datasource @datasource}]
                      (< 0
                         (count
                          (query conn
                                 (-> (select :*)
                                     (from :api_users)
                                     (where [:= :username username])
                                     (sql/format)))
                          )
                         )))

(defn create-user [firstname lastname email username password]
  (with-db-connection [conn {:datasource @datasource}]
                      (execute! conn
                                (-> (insert-into :api_users)
                                    (columns :firstname :lastname :email :username :password)
                                    (values [[firstname lastname email username password]])
                                    (sql/format)
                                    ))))