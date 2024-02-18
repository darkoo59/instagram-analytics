(ns instagram-analytics.core
  (:require
    [clojure.core :refer :all]
    [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
    [ring.middleware.session :refer [wrap-session]]
    [ring.middleware.params :refer [wrap-params]]
    [compojure.core :refer [defroutes GET POST PATCH DELETE]]
    [ring.util.response :refer [response bad-request]]
    [ring.middleware.session-timeout :refer [wrap-idle-session-timeout wrap-absolute-session-timeout]]
    [instagram-analytics.controller.controller :refer [test]]
    )
  )

(defroutes routes-handler
           (POST "/test" request (test request))
           )

(def app
  (-> routes-handler
      (wrap-idle-session-timeout
        {:timeout-response (response {:status 2 :error "Your session have expired"})
         :timeout 100})
      (wrap-session)
      (wrap-json-params)
      (wrap-params)
      (wrap-json-response)))
