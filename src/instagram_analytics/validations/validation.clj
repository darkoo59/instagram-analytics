;; This namespace provides functions for validating request parameters.
(ns instagram-analytics.validations.validation
  (:require
    [clojure.core :refer :all]
    [clojure.spec.alpha :as s]
    [instagram-analytics.spec.spec
     :refer
     :all]
    [instagram-analytics.utils.token :refer [unsign-token]]
    [instagram-analytics.services.user :refer [is-user-verificated? is-username-verificated?]]))

;; Validates parameters for login request.
(defn validate-login-request [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")]
    (if (validate-login-params username password)
      request
      (throw (Exception. "Params for login request aren't valid")))))

;; Validates parameters for registration request.
(defn validate-registration-request [request params]
  (let [firstname (get params "firstname")
        lastname (get params "lastname")
        email (get params "email")
        password (get params "password")
        username (get params "username")]
    (if (validate-registration-params firstname lastname email password username)
      request
      (throw (Exception. "Params for registration request aren't valid")))))

;; Validates user credentials.
(defn validate-credentials [request params]
  (let [authentication (get params "authentication")
        username       (get authentication "username")
        password       (get authentication "password")]
    (if (is-user-verificated? username password)
      [request params]
      (throw (Exception. "User isn't verificated")))))

;; Validates JWT token.
(defn validate-token [request params]
  (let [token          (get params "token")
        unsigned-token (unsign-token token)
        username       (get unsigned-token :username)]
    (if (and (some? username) (is-username-verificated? username))
      request
      (throw (Exception. "Token isn't valid")))))

(defn validate-all-posts-request [request params]
  (let [token          (get params "token")]
    (if (validate-token token)
      request
      (throw (Exception. "Params for all posts request aren't valid")))))

;; Validates parameters for retrieving all posts request.
(defn validate-top-n-posts-request [request params]
  (let [token          (get params "token")
        n              (get params "num")
        column-name    (get params "column-name")]
    (if (validate-top-n-posts-params token n column-name)
      request
      (throw (Exception. "Params for top n posts request aren't valid")))))

;; Validates parameters for retrieving top N posts request.
(defn validate-posts-by-type-request [request params]
  (let [token          (get params "token")
        type           (get params "type")]
    (if (validate-posts-by-type-params token type)
      request
      (throw (Exception. "Params for posts by type request aren't valid")))))