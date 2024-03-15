(ns instagram-analytics.services.posts
  (:require
    [clojure.core]
    [instagram-analytics.services.csv_data :refer [csv-data]]
    [instagram-analytics.constant.csv-headers :refer [link-header post-type reach]]
    [cheshire.core :refer [generate-string]]))

(defn all-posts []
  (let [data @csv-data]
    (let [header (first data)]
      (if (not (nil? (.indexOf header link-header)))
        (->> data
             (rest)
             (map (fn [row] (nth row (.indexOf header link-header)))))
        (generate-string {:error (str "Error during reading all posts")})))))

(defn top-n-posts [n column-name]
  (let [data @csv-data]
    (let [header (first data)]
      (if (not (nil? (.indexOf header column-name)))
        (->> data
             (rest)
             (map
              (fn [row]
                {:link  (nth row (.indexOf header link-header))
                 :count (Integer. (nth row (.indexOf header column-name)))}))
             (sort-by :count >)
             (take n))
        ; Convert to JSON string
        (generate-string
         {:error (str "Column \"" column-name "\" can't be found in CSV file.")})))))

(defn posts-by-type [type]
  (let [data   @csv-data
        header (first data)]
    (if-let [type-column-index (.indexOf (first data) post-type)]
      (->> data
           (rest)
           (filter #(= type (nth % type-column-index)))
           (map (fn [row] (nth row (.indexOf header link-header)))))
      (println "Column " post-type " can't be found in CSV file."))))