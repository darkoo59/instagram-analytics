(ns instagram-analytics.core
    (:require
      [clojure.core :refer :all]
      [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
      [ring.middleware.session :refer [wrap-session]]
      [ring.middleware.params :refer [wrap-params]]
      [compojure.core :refer [defroutes GET POST PATCH DELETE]]
      [ring.util.response :refer [response bad-request]]
      [ring.middleware.session-timeout
       :refer
       [wrap-idle-session-timeout wrap-absolute-session-timeout]]
      [instagram-analytics.controller.controller :refer [login-controller launch-controller all-posts-controller]]))

(defroutes routes-handler
  (POST "/instagram-analytics-api/login" request (login-controller request))
  (POST "/instagram-analytics-api/launch" request (launch-controller request))
  (GET "/instagram-analytics-api/all-posts" request (all-posts-controller request))
  )

(def app
  (-> routes-handler
;      (wrap-idle-session-timeout
;       {:timeout-response (response {:status 2 :error "Your session have expired"})
;        :timeout          100})
;      (wrap-session)
      (wrap-json-params)
      (wrap-params)
      (wrap-json-response)))
