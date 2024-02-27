(ns instagram-analytics.routes-tests
  (:require [clojure.test :refer :all]
            [instagram-analytics.core :refer :all]
            [midje.sweet :refer [facts fact]]
            [ring.mock.request :as mock]))

(deftest test-app
  (testing "not-found route"
           (let [response (app (mock/request :get "/invalid"))]
             (is (= (:status response) 404))
             (is (= (:body response) "Not found")))))
