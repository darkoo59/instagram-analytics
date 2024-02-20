(ns instagram-analytics.services.csv_data
  (:require
    [clojure.core]
    [clojure.data.csv :as csv]
    [instagram-analytics.constant.data :refer [csv-file-path]]
    [clojure.java.io :as io]))

(def csv-data (atom nil))

(defn load-csv-data []
  (let [resource-url (io/resource csv-file-path)]
    (with-open [reader (io/reader (.getFile resource-url))]
      (doall (csv/read-csv reader)))))

(defn load-csv []
  (if @csv-data
    (do
      (println "CSV data already loaded.")
      "already added")
    (do
      (reset! csv-data (load-csv-data))
      (println "CSV data loaded.")
      "added now")))