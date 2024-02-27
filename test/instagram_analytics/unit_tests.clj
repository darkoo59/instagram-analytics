(ns instagram-analytics.unit-tests
  (:require [clojure.test :refer :all]
            [instagram-analytics.services.csv_data :refer [load-csv-data load-csv csv-data]]
            [instagram-analytics.constant.data :refer [csv-file-path]]
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