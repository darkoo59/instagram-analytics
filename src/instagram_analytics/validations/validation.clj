(ns instagram-analytics.validations.validation
  (:require
    [clojure.core :refer :all]
    [clojure.spec.alpha :as s]
    [instagram-analytics.spec.spec :refer [validate-login-params]]
    [instagram-analytics.utils.token :refer [unsign-token]]
    [instagram-analytics.services.user :refer [is-user-verificated? is-username-verificated?]]))

(defn validate-login-request [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")
        token          (get (get request :query-params) "token")]
    (if (validate-login-params username password token)
      request
      (throw (Exception. "Params for login request aren't valid")))))

(defn validate-credentials [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")]
    (if (is-user-verificated? username password)
      [request params]
      (throw (Exception. "User isn't verificated")))))

(defn validate-token [request params]
  (let [token          (get params "token")
        unsigned-token (unsign-token token)
        username       (get unsigned-token :username)]
    (if (and (some? username) (is-username-verificated? username))
      request
      (throw (Exception. "Token isn't valid")))))