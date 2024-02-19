(ns instagram-analytics.handler.handler
  (:require
    [ring.util.response :refer [response]])
  )

(defn login-handler [request params]
  (response {:message "All good"} )
  )