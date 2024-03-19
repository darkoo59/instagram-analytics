;; This namespace defines constants representing headers for CSV files (constants on serbian language because of
;; csv file that we get from instagram for testing purpose).
(ns instagram-analytics.constant.csv-headers
  (:require
    [clojure.core]))

;; Represents the header for the link column in CSV files.
(def link-header "Стална веза")

;; Represents the header for the post type column in CSV files.
(def post-type "Тип објаве")

;; Represents the header for the reach column in CSV files.
(def reach "Домет")

;; Represents the header for the number of comments column in CSV files.
(def comments-number "Број коментара")

;; Represents the header for the number of likes column in CSV files.
(def likes "Свиђања")