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
           (map (fn [row] (nth row (.indexOf header "Стална веза")))))
      ; Convert to JSON string
      (generate-string {:error (str "Greska prilikom citanja svih objava")})))))

(defn top-n-posts [n column-name]
  (let [data @csv-data]
    (let [header (first data)]
      (if (not (nil? (.indexOf header column-name)))
        (->> data
             (rest)
             (map
              (fn [row]
                {:link  (nth row (.indexOf header "Стална веза"))
                 :count (Integer. (nth row (.indexOf header column-name)))}))
             (sort-by :count >)
             (take n))
        ; Convert to JSON string
        (generate-string
         {:error (str "Kolona \"" column-name "\" nije pronađena u CSV fajlu.")})))))

(defn posts-by-type [type]
  (let [data   @csv-data
        header (first data)]
    (if-let [type-column-index (.indexOf (first data) "Тип објаве")]
      (->> data
           (rest)
           (filter #(= type (nth % type-column-index)))
           (map (fn [row] (nth row (.indexOf header "Стална веза")))))
      (println "Kolona 'Тип објаве' nije pronadjena u CSV fajlu."))))