(ns instagram-analytics.handler.handler
  (:require
    [ring.util.response :refer [response]]
    [instagram-analytics.services.csv_data :refer [load-csv]]
    [instagram-analytics.utils.token :refer [secret make-token]]
    [instagram-analytics.services.posts
     :refer
     [all-posts top-n-posts posts-by-type]]
    [cheshire.core :refer [generate-string]])
  )

(defn login-handler [request params]
  (response {:access_token (make-token (get (get params "authentication") "username"))} )
  )

(defn launch-handler [request params]
  (load-csv)
  (response {:message "CSV data loaded successfully!"})
  )

(defn all-posts-handler [request params]
  (let [posts (all-posts)]
    (response (generate-string {:posts posts}))))

(defn top-n-posts-handler [request params]
  (let [posts (top-n-posts (get params "num") (get params "column-name"))]
    (response (generate-string {:posts posts}))))

(defn posts-by-type-handler [request params]
  (let [posts (posts-by-type (get params "type"))]
    (response (generate-string {:posts posts}))))