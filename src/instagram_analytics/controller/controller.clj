(ns instagram-analytics.controller.controller
  (:require
    [clojure.core :refer :all]
    [instagram-analytics.utils.response_helper :refer [handle-bad-request]]
    [instagram-analytics.validations.validation :refer [validate-login-request validate-credentials validate-token]]
    [instagram-analytics.handler.handler :refer [login-handler]]
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