;; This namespace provides functions for generating and validating JWT tokens.
(ns instagram-analytics.utils.token
  (:require
    [buddy.sign.jwt :refer [sign unsign]]
    [environ.core :refer [env]]
    [ring.util.response :refer [response]]))

;; The secret key used for JWT token generation and validation.
(defonce secret (env :token-secret))

;; Generates a JWT token for the provided username.
(defn make-token [username]
  (sign {:username username} secret))

;; Validates and extracts the username from a JWT token.
(defn unsign-token [token]
  (unsign token secret))