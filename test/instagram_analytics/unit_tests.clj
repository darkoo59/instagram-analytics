(ns instagram-analytics.unit-tests
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [instagram-analytics.services.csv_data :refer [load-csv-data load-csv csv-data]]
            [instagram-analytics.constant.data :refer [csv-file-path]]
            [instagram-analytics.validations.validation :refer [validate-login-request validate-registration-request validate-all-posts-request]]
            [cheshire.core :refer [generate-string]]
            [instagram-analytics.spec.spec :refer :all]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(deftest load-csv-data-test
  (testing "Test that load-csv-data returns the correct data"
           (let [testing-file-path "test.csv"
                 test-csv-data     [["Link" "Type" "Description"]
                                    ["test/link/1" "reels" "Very good reel"]
                                    ["test/link/2" "post" "Very good post"]]]
             (with-redefs [csv-file-path testing-file-path]
               (is (= test-csv-data (load-csv-data)))))))

(deftest load-csv-test
  (testing "Test that load-csv returns the correct result"
           (let [test-csv-data [["Link" "Type" "Description"]
                                ["test/link/1" "reels" "Very good reel"]
                                ["test/link/2" "post" "Very good post"]]]
             (with-redefs [csv-data      (atom nil)
                           load-csv-data (fn [] test-csv-data)]
               (is (= "added now" (load-csv)))))))

(deftest load-csv-already-loaded-test
  (testing "Test that load-csv handles the case where the data is already loaded"
           (let [test-csv-data [["Link" "Type" "Description"]
                                ["test/link/1" "reels" "Very good reel"]
                                ["test/link/2" "post" "Very good post"]]]
             (with-redefs [csv-data      (atom test-csv-data)
                           load-csv-data (fn [] csv-data)]
               (is (= "already added" (load-csv)))))))

(deftest validate-login-params-test
  (testing "Test that validates request params for login"
           (is (validate-login-params "username" "password"))))

(deftest validate-registration-params-test
  (testing "Test that validates request params for registration"
           (is (validate-registration-params "valid_firstname" "valid_lastname" "valid.email@gmail.com" "valid_password" "valid_username"))
           (is (not (validate-registration-params "valid_firstname" "valid_lastname" "invalid_email" "valid_password" "valid_username")))))

(deftest validate-top-n-posts-params-test
  (testing "Test that validates request params for fetching top N posts"
           (is (validate-top-n-posts-params "dbb0b9a4-a775-40cb-9e2f-0618148214d5" 10 "valid_column"))
           (is (validate-top-n-posts-params "dbb0b9a4-a775-40cb-9e2f-0618148214d5" 0 "valid_column")) ; Testing with zero value for N
           (is (not (validate-top-n-posts-params "invalid_token" 10 "valid_column"))) ; Invalid token
           (is (not (validate-top-n-posts-params "invalid_token" 10 nil))) ; Invalid column
           (is (not (validate-top-n-posts-params "dbb0b9a4-a775-40cb-9e2f-0618148214d5" -5 "valid_column"))) ; Negative value for N
           ))

(deftest validate-posts-by-type-params-test
  (testing "Test that validates request params for fetching posts by type"
           (is (validate-posts-by-type-params "dbb0b9a4-a775-40cb-9e2f-0618148214d5" "valid_type"))
           (is (not (validate-posts-by-type-params "invalid_token" "valid_type"))) ; Invalid token
           (is (not (validate-posts-by-type-params "dbb0b9a4-a775-40cb-9e2f-0618148214d5" nil))) ; Invalid type
           ))

(deftest validate-login-request-test
  (testing "Test that check if login request throw exception for wrong params"
           (facts
           (validate-login-request {:request true} nil) => (throws Exception "Params for login request aren't valid"))))

(deftest validate-registration-request-test
  (testing "Test that check if registration request throw exception for wrong params"
           (facts
            (validate-registration-request {:request true} nil) => (throws Exception "Params for registration request aren't valid"))))