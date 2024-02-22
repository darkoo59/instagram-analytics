(ns instagram-analytics.services.posts
  (:require
    [clojure.core]
    [instagram-analytics.services.csv_data :refer [csv-data]]
    [cheshire.core :refer [generate-string]]))

(defn all-posts []
(let [data @csv-data]
  (let [header (first data)]
    (if (not (nil? (.indexOf header "Стална веза")))
      (->> data
           (rest)
           (map (fn [row] {:id-objave (nth row (.indexOf header "Стална веза"))}))
           (take 10)) ; Convert to JSON string
      (generate-string {:error (str "Greska prilikom citanja svih objava")})))))