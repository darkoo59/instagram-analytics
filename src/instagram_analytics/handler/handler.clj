(ns instagram-analytics.handler.handler
  (:require
    [ring.util.response :refer [response]]
    [instagram-analytics.services.csv_data :refer [load-csv]]
    [instagram-analytics.services.user :refer [create-user]]
    [instagram-analytics.utils.token :refer [secret make-token]]
    [instagram-analytics.services.statistics :refer [calculate-post-type-percentages calculate-average-type-reach calculate-engagement-for-type]]
    [instagram-analytics.services.posts
     :refer
     [all-posts top-n-posts posts-by-type]]
    [cheshire.core :refer [generate-string]])
  )

(defn login-handler [request params]
  (load-csv)
  (response {:access_token (make-token (get (get params "authentication") "username"))} )
  )

(defn registration-handler [request params]
  (create-user (get params "firstname") (get params "lastname") (get params "email") (get params "username") (get params "password"))
  (response {:message "Successfully registered new user"} )
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

(defn type-percentages-handler [request params]
  (let [percentages (calculate-post-type-percentages)]
    (response (generate-string {:percentages percentages}))))

(defn type-reach-handler [request params]
  (let [percentages (calculate-average-type-reach)]
    (response (generate-string {:percentages percentages}))))

(defn type-engagement-handler [request params]
  (let [percentages (calculate-engagement-for-type)]
    (response (generate-string {:percentages percentages}))))