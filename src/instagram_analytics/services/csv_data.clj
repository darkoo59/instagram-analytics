;; This namespace contains functions for loading CSV data from a file.
(ns instagram-analytics.services.csv_data
  (:require
    [clojure.core]
    [clojure.data.csv :as csv]
    [instagram-analytics.constant.data :refer [csv-file-path]]
    [clojure.tools.logging :refer [info]]
    [clojure.java.io :as io])
  (:import (org.apache.log4j Logger Level)))

;; An atom to hold the loaded CSV data. It is initially set to nil and later populated
;; with the content of the CSV file.
(def csv-data (atom nil))

;; Reads the CSV file from the provided file path and returns the data as a sequence of
;; rows, where each row is represented as a vector of strings.
(defn load-csv-data []
  (let [resource-url (io/resource csv-file-path)]
    (with-open [reader (io/reader (.getFile resource-url))]
      (doall (csv/read-csv reader)))))

;; Loads the CSV data from the file if it hasn't been loaded already. If the data is
;; already loaded, it logs a message indicating that it's already loaded.
(defn load-csv []
  (if @csv-data
    (do
      (info "CSV data already loaded.")
      "already added")
    (do
      (reset! csv-data (load-csv-data))
      (info "CSV data loaded.")
      "added now")))