;; This namespace contains specifications and validation functions for input parameters.
(ns instagram-analytics.spec.spec
  (:require
    [clojure.core :refer :all]
    [clojure.spec.alpha :as s])
  (:import [java.util UUID]))

(s/def :login/username string?)
(s/def :login/password string?)
(s/def :registration/firstname string?)
(s/def :registration/lastname string?)
(s/def :registration/email
       (s/and string?
              #(re-matches #"\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b" %)))
(s/def :column/column string?)
(s/def :type/type string?)
(s/def :num/num  (s/and int? #(>= % 0)))
(s/def :token/token (s/and string?
                           (s/conformer
                            (fn [u]
                              (try
                                (UUID/fromString u)
                                (catch IllegalArgumentException _))))
                           uuid?))

;; Specification for user login
(s/def :unq/login
       (s/keys :req-un
               [:login/username :login/password]))

;; Specification for user registration
(s/def :unq/registration
       (s/keys :req-un
               [:registration/firstname :registration/lastname :registration/email :login/password :login/username]))

;; Specification for fetching top N posts
(s/def :unq/top-n
       (s/keys :req-un
         [:token/token :column/column]
               :opt-un
         [:num/num]))

;; Specification for fetching all posts by type
(s/def :unq/by-type
       (s/keys :req-un
               [:token/token :type/type]))

;; Validates login parameters.
(defn validate-login-params [username password]
  (s/valid? :unq/login {:username username :password password}))

;; Validates registration parameters.
(defn validate-registration-params [firstname lastname email password username]
  (s/valid? :unq/registration {:firstname firstname :lastname lastname :email email :password password :username username}))

;; Validates parameters for fetching top N posts.
(defn validate-top-n-posts-params [token n column]
  (s/valid? :unq/top-n
            {:token token :num n :column column}))

;; Validates parameters for fetching posts by type.
(defn validate-posts-by-type-params [token type]
  (s/valid? :unq/by-type {:token token :type type}))