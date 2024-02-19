(ns instagram-analytics.spec.spec
  (:require
    [clojure.core :refer :all]
    [clojure.spec.alpha :as s])
  (:import [java.util UUID]))

(s/def :username string?)
(s/def :password string?)
(s/def :token (s/and string?
                     (s/conformer
                      (fn [u]
                        (try
                          (UUID/fromString u)
                          (catch IllegalArgumentException _))))
                     (s/conformer
                      #(str %))))

(s/def :login
       (s/keys :req-un
               [:username :password :token]))

(defn validate-login-params [username password token]
  (s/valid? :login {:username username :password password :token token})
  )