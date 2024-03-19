;; This namespace provides helper functions for generating HTTP responses.
(ns instagram-analytics.utils.response_helper
  (:require
    [clojure.core :refer :all]
    [ring.util.response :refer [bad-request response]]))

;; Generates a bad request response with the provided error message.
(defn handle-bad-request [message]
  (bad-request {:error message}))