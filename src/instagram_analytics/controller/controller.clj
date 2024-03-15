(ns instagram-analytics.controller.controller
  (:require
    [clojure.core :refer :all]
    [instagram-analytics.utils.response_helper :refer [handle-bad-request]]
    [instagram-analytics.validations.validation
     :refer
     :all]
    [instagram-analytics.handler.handler
     :refer
     :all]
    ))

(defn login-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-login-request params)
          (validate-credentials params)
          (login-handler params))
       (catch Exception e (handle-bad-request (str e)))))
    )

(defn registration-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-registration-request params)
          (registration-handler params))
      (catch Exception e (handle-bad-request (str e)))))
  )

(defn all-posts-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-token params)
          (all-posts-handler params))
      (catch Exception e (handle-bad-request (str e))))))

(defn top-n-posts-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-top-n-posts-request params)
          (validate-credentials params)
          (validate-token params)
          (top-n-posts-handler params))
      (catch Exception e (handle-bad-request (str e))))))

(defn posts-by-type-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-posts-by-type-request params)
          (validate-token params)
          (posts-by-type-handler params))
      (catch Exception e (handle-bad-request (str e))))))

(defn type-percentages-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (type-percentages-handler params))
      (catch Exception e (handle-bad-request (str e))))))

(defn type-reach-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (type-reach-handler params))
      (catch Exception e (handle-bad-request (str e))))))

(defn type-engagement-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (type-engagement-handler params))
      (catch Exception e (handle-bad-request (str e))))))