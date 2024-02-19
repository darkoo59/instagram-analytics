(ns instagram-analytics.utils.token
  (:require
    [buddy.sign.jwt :refer [sign unsign]]
    [environ.core :refer [env]]
    [ring.util.response :refer [response]]))

(defonce secret (env :token-secret))

(defn unsign-token [token]
  (unsign token secret))