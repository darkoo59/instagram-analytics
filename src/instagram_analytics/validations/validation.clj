(ns instagram-analytics.validations.validation
  (:require
    [clojure.core :refer :all]
    [clojure.spec.alpha :as s]
    [instagram-analytics.spec.spec
     :refer
     [validate-login-params
      validate-top-n-posts-params
      validate-posts-by-type-params]]
    [instagram-analytics.utils.token :refer [unsign-token]]
    [instagram-analytics.services.user :refer [is-user-verificated? is-username-verificated?]]))

(defn validate-login-request [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")]
    (if (validate-login-params username password)
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

(defn validate-launch-request [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")
        token          (get (get request :query-params) "token")]
    (if (validate-login-params username password token)
      request
      (throw (Exception. "Params for launch request aren't valid")))))

(defn validate-all-posts-request [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")
        token          (get (get request :query-params) "token")]
    (if (validate-login-params username password token)
      request
      (throw (Exception. "Params for all posts request aren't valid")))))

(defn validate-top-n-posts-request [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")
        token          (get params "token")
        n              (get params "num")
        column-name    (get params "column-name")]
    (if (validate-top-n-posts-params username password token n column-name)
      request
      (throw (Exception. "Params for top n posts request aren't valid")))))

(defn validate-posts-by-type-request [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")
        token          (get params "token")
        type           (get params "type")]
    (if (validate-posts-by-type-params username password token type)
      request
      (throw (Exception. "Params for top n posts request aren't valid")))))