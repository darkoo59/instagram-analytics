(ns instagram-analytics.spec.spec
  (:require
    [clojure.core :refer :all]
    [clojure.spec.alpha :as s])
  (:import [java.util UUID]))

(s/def :login/username string?)
(s/def :login/password string?)
(s/def :column/column string?)
(s/def :num/num int?)
(s/def :token/token (s/and string?
                     (s/conformer
                      (fn [u]
                        (try
                          (UUID/fromString u)
                          (catch IllegalArgumentException _))))
                     (s/conformer
                      #(str %))))

(s/def :unq/login
       (s/keys :req-un
               [:login/username :login/password :token/token]))

(s/def :unq/top-n
       (s/keys :req-un
               [:login/username :login/password :token/token :column/column]
               :opt-un
               [:num/num]))

(defn validate-login-params [username password token]
  (s/valid? :unq/login {:username username :password password :token token}))

(defn validate-top-n-posts-params [username password token n column]
  (s/valid? :unq/top-n {:username username :password password :token token :num n :col column}))