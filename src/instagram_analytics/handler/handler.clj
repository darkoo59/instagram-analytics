(ns instagram-analytics.handler.handler
  (:require
    [ring.util.response :refer [response]]
    [instagram-analytics.services.csv_data :refer [load-csv]])
  )

(defn login-handler [request params]
  (response {:message "All good"} )
  )

(defn launch-handler [request params]
  (load-csv)
  (response {:message "CSV data loaded successfully!"})
  )