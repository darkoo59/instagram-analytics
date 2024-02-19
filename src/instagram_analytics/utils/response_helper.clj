(ns instagram-analytics.utils.response_helper
  (:require
    [clojure.core :refer :all]
    [ring.util.response :refer [bad-request response]]))

(defn handle-bad-request [message]
  (bad-request {:error message}))