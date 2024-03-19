;; This namespace contains controller functions responsible for handling HTTP requests and delegating
;; request processing to corresponding handler functions.
(ns instagram-analytics.controller.controller
  (:require
    [clojure.core :refer :all]
    [instagram-analytics.utils.response_helper :refer [handle-bad-request]]
    [instagram-analytics.validations.validation
     :refer
     :all]
    [instagram-analytics.handler.handler
     :refer
     :all]))

;; Handles the login request by validating the request parameters, credentials, and then delegates
;; the request processing to the login-handler.
(defn login-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-login-request params)
          (validate-credentials params)
          (login-handler params)
          )
      (catch Exception e (handle-bad-request (str e))))))

;; Handles the registration request by validating the request parameters and then delegates
;; the request processing to the registration-handler.
(defn registration-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-registration-request params)
          (registration-handler params))
      (catch Exception e (handle-bad-request (str e))))))

;; Handles the request to retrieve all posts by validating the authentication token and then
;; delegates the request processing to the all-posts-handler.
(defn all-posts-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-token params)
          (all-posts-handler params))
      (catch Exception e (handle-bad-request (str e))))))

;; Handles the request to retrieve the top N posts by validating the request parameters,
;; credentials, and authentication token, then delegates the request processing to the
;; top-n-posts-handler.
(defn top-n-posts-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-top-n-posts-request params)
          (validate-token params)
          (top-n-posts-handler params))
      (catch Exception e (handle-bad-request (str e))))))

;; Handles the request to retrieve posts by type by validating the request parameters
;; and authentication token, then delegates the request processing to the posts-by-type-handler.
(defn posts-by-type-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (validate-posts-by-type-request params)
          (validate-token params)
          (posts-by-type-handler params))
      (catch Exception e (handle-bad-request (str e))))))

;; Handles the request to retrieve type percentages and delegates the request processing
;; to the type-percentages-handler.
(defn type-percentages-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (type-percentages-handler params))
      (catch Exception e (handle-bad-request (str e))))))

;; Handles the request to retrieve type reach and delegates the request processing
;; to the type-reach-handler.
(defn type-reach-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (type-reach-handler params))
      (catch Exception e (handle-bad-request (str e))))))

;; Handles the request to retrieve type engagement and delegates the request processing
;; to the type-engagement-handler.
(defn type-engagement-controller [request]
  (let [params (:params request)]
    (try
      (-> request
          (type-engagement-handler params))
      (catch Exception e (handle-bad-request (str e))))))