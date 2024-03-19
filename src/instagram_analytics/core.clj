;; This namespace defines the main entry point for the backend application, setting up
;; routes, middleware, and the main application handler.
(ns instagram-analytics.core
  (:require
    [clojure.core :refer :all]
    [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
    [ring.middleware.session :refer [wrap-session]]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.cors :refer [wrap-cors]]
    [compojure.core :refer [defroutes GET POST PATCH DELETE]]
    [compojure.route :as route]
    [ring.util.response :refer [response bad-request]]
    [ring.middleware.session-timeout
     :refer
     [wrap-idle-session-timeout wrap-absolute-session-timeout]]
    [instagram-analytics.controller.controller
     :refer
     :all]))


;; Defines the routes for handling different HTTP requests. Each route corresponds to
;; a specific controller function responsible for processing the request.
(defroutes routes-handler
  (POST "/instagram-analytics-api/login" request (login-controller request))
  (POST "/instagram-analytics-api/registration" request (registration-controller request))
  (GET "/instagram-analytics-api/all-posts" request (all-posts-controller request))
  (GET "/instagram-analytics-api/top-n-posts" request (top-n-posts-controller request))
  (GET "/instagram-analytics-api/posts-by-type" request (posts-by-type-controller request))
  (GET "/instagram-analytics-api/statistics/percentages" request (type-percentages-controller request))
  (GET "/instagram-analytics-api/statistics/reach" request (type-reach-controller request))
  (GET "/instagram-analytics-api/statistics/engagement" request (type-engagement-controller request))
  (route/not-found "Not found"))

;; The main application handler that combines routes with middleware.
(def app
  (-> routes-handler
      (wrap-idle-session-timeout
       {:timeout-response (response {:status 2 :error "Your session have expired"})
        :timeout          100})
      (wrap-session)
      (wrap-json-params)
      (wrap-params)
      (wrap-json-response)
      (wrap-cors :access-control-allow-origin  [#"http://localhost:8080/*"]
                 :access-control-allow-methods [:get :put :post :delete])))
