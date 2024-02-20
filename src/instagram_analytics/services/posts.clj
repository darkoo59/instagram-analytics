(ns instagram-analytics.services.posts
  (:require
    [clojure.core]
    [instagram-analytics.services.csv_data :refer [csv-data]]
    [cheshire.core :refer [generate-string]]))

(defn top-n-posts [n column-name]
(let [data @csv-data]  (let [header (first data)
        likes-column-name "Свиђања"
        saves-column-name "Чувања"]
    (if (not (nil? (.indexOf header column-name)))
      (->> data
           (rest)
           (map (fn [row] {:id-objave (nth row (.indexOf header "Стална веза")) :count (Integer. (nth row (.indexOf header column-name)))}))
           (sort-by :count >)
           (take n)) ; Convert to JSON string
      (generate-string {:error (str "Kolona \"" column-name "\" nije pronađena u CSV fajlu.")})))))