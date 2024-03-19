(ns instagram-analytics.routes-tests
  (:require [clojure.test :refer :all]
            [instagram-analytics.core :refer :all]
            [instagram-analytics.handler.handler :refer [login-handler]]
            [instagram-analytics.services.user :refer [is-user-verificated? create-user is-username-verificated?]]
            [instagram-analytics.validations.validation :refer [validate-credentials]]
            [instagram-analytics.services.posts :refer [all-posts]]
            [instagram-analytics.utils.token :refer [unsign-token]]
            [ring.util.response :refer [response]]
            [instagram-analytics.services.csv_data :refer [load-csv]]
            [instagram-analytics.utils.token :refer [secret make-token]]
            [cheshire.core :refer [generate-string parse-string]]
            [midje.sweet :refer [facts fact]]
            [ring.mock.request :as mock]))

(deftest route-not-found-test
  (testing "Testing when route doesn't exist"
           (let [response (app (mock/request :get "/invalid"))]
             (is (= (:status response) 404))
             (is (= (:body response) "Not found")))))

(deftest login-request-test
  (testing "Testing login request"
           (with-redefs [is-user-verificated? (fn [username password] true)
                         load-csv             (fn [])]
             (let [response (app
                             (->
                              (mock/request :post "/instagram-analytics-api/login"
                                            (generate-string {:authentication {:username "username" :password "password"}}))
                              (mock/content-type "application/json")))]
               (is (= (:status response) 200))
               (is (contains? (parse-string (:body response) true) :access_token))))))

(deftest registration-request-test
  (testing "Testing registration request"
           (with-redefs [create-user (fn [firstname lastname email username password])]
             (let [response (app
                             (->
                              (mock/request :post "/instagram-analytics-api/registration"
                                            (generate-string
                                             {:firstname "firstname_test"
                                              :lastname  "lastname_test"
                                              :email     "email@test.com"
                                              :password  "password_test"
                                              :username  "username_test"}))
                              (mock/content-type "application/json")))
                   body-map (parse-string (:body response) true)]
               (is (= (:status response) 200))
               (is (contains? body-map :message))
               (is (= (:message body-map) "Successfully registered new user"))))))

(deftest all-posts-request-test
  (testing "Testing all posts request"
           (with-redefs [all-posts (fn [] ["instagram.com/link1" "instagram.com/link2" "instagram.com/link3"])
                         unsign-token (fn [token] {:username "username"})
                         is-username-verificated? (fn [username] true)]
             (let [response (app
                             (->
                              (mock/request :get "/instagram-analytics-api/all-posts"
                                            (generate-string
                                             {:token "dbb0b9a4-a775-40cb-9e2f-0618148214d5"}))
                              (mock/content-type "application/json")))
                   body-map (parse-string (:body response) true)]
               (println response)
               (is (= (:status response) 200))
               (is (contains? body-map :posts))
               (is (= (:posts body-map) ["instagram.com/link1" "instagram.com/link2" "instagram.com/link3"]))))))