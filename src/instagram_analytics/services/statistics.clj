(ns instagram-analytics.services.statistics
  (:require
    [clojure.core]
    [instagram-analytics.services.csv_data :refer [csv-data]]
    [cheshire.core :refer [generate-string]]))

(defn calculate-post-type-percentages []
  (let [data @csv-data
        header (first data)]
    (if-let [type-column-index (.indexOf (first data) "Тип објаве")]
      (let [total-posts (count data)
            type-counts (->> data
                             (rest)
                             (map #(nth % type-column-index))
                             (frequencies))]
        (into {}
              (for [[type count] type-counts]
                [type (float (/ (* 100 count) total-posts))])))
      (println "Kolona 'Тип објаве' nije pronadjena u CSV fajlu."))))

(defn calculate-average-type-reach []
  (let [data @csv-data
        header (first data)]
    (if-let [type-column-index (.indexOf (first data) "Тип објаве")]
      (let [total-posts (count data)
            type-counts (->> data
                             (rest)
                             (map #(nth % type-column-index))
                             (frequencies))
            _ (println "TYPE COUNTS")
            _ (println type-counts)
            type-reach (->> data
                            (rest)
                            (map #(nth % type-column-index))
                            (map #(nth % (.indexOf header "Домет")))
                            (frequencies))
            _ (println "TYPE REACH")
            _ (println (.indexOf header "Домет"))]
        (into {}
              (for [[type count] type-counts]
                [type {:percentage (float (/ (* 100 count) total-posts))
                       :average-reach (float (/ (apply + (filter #(= type %) type-reach)) count))}]))
        (println "Kolona 'Тип објаве' nije pronadjena u CSV fajlu.")))))