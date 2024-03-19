;; This namespace contains handler functions responsible for processing various requests
;; received by the controller.
(ns instagram-analytics.handler.handler
  (:require
    [ring.util.response :refer [response]]
    [instagram-analytics.services.csv_data :refer [load-csv]]
    [instagram-analytics.services.user :refer [create-user]]
    [instagram-analytics.utils.token :refer [secret make-token]]
    [instagram-analytics.services.statistics :refer :all]
    [instagram-analytics.services.posts
     :refer
     [all-posts top-n-posts posts-by-type]]
    [cheshire.core :refer [generate-string]]))

;; Handles the login request by loading CSV data and generating an access token for the user.
(defn login-handler [request params]
  (load-csv)
  (response
   {:access_token (make-token (get (get params "authentication") "username"))}))

;; Handles the registration request by creating a new user and sending a success message.
(defn registration-handler [request params]
  (create-user (get params "firstname") (get params "lastname") (get params "email") (get params "username") (get params "password"))
  (response {:message "Successfully registered new user"}))

;; Handles the launch request by loading CSV data and sending a success message.
(defn launch-handler [request params]
  (load-csv)
  (response {:message "CSV data loaded successfully!"}))

;; Handles the request to retrieve all posts and sends the posts as JSON response.
(defn all-posts-handler [request params]
  (let [posts (all-posts)]
    (response (generate-string {:posts posts}))))

;; Handles the request to retrieve the top N posts based on specified criteria
;; and sends the posts as JSON response.
(defn top-n-posts-handler [request params]
  (let [posts (top-n-posts (get params "num") (get params "column-name"))]
    (response (generate-string {:posts posts}))))

;; Handles the request to retrieve posts by type and sends the posts as JSON response.
(defn posts-by-type-handler [request params]
  (let [posts (posts-by-type (get params "type"))]
    (response (generate-string {:posts posts}))))

;; Handles the request to calculate and retrieve type percentages and sends the percentages as JSON response.
(defn type-percentages-handler [request params]
  (let [percentages (calculate-post-type-percentages)]
    (response (generate-string {:percentages percentages}))))

;; Handles the request to calculate and retrieve type reach and sends the reach as JSON response.
(defn type-reach-handler [request params]
  (let [percentages (calculate-average-type-reach)]
    (response (generate-string {:percentages percentages}))))

;; Handles the request to calculate and retrieve type engagement and sends the engagement as JSON response.
(defn type-engagement-handler [request params]
  (let [percentages (calculate-engagement-for-type)]
    (response (generate-string {:percentages percentages}))))