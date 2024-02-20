(ns instagram-analytics.controller.controller
  (:require
    [clojure.core :refer :all]
    [instagram-analytics.utils.response_helper :refer [handle-bad-request]]
    [instagram-analytics.validations.validation :refer [validate-login-request validate-credentials validate-token validate-launch-request]]
    [instagram-analytics.handler.handler :refer [login-handler launch-handler]]
    ))

(defn login-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-login-request params)
          (validate-credentials params)
          (validate-token params)
          (login-handler params))
       (catch Exception e (handle-bad-request (str e)))))
    )

(defn launch-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-launch-request params)
          (validate-credentials params)
          (validate-token params)
          (launch-handler params))
      (catch Exception e (handle-bad-request (str e)))))
  )