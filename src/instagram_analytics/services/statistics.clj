;; This namespace contains functions for calculating various statistics based on CSV data.
(ns instagram-analytics.services.statistics
  (:require
    [clojure.core :refer :all]
    [clojure.tools.logging :refer [warn]]
    [instagram-analytics.services.csv_data :refer [csv-data]]
    [instagram-analytics.constant.csv-headers
     :refer
     [link-header post-type reach comments-number likes]]
    [cheshire.core :refer [generate-string]]))

;; Extracts the index of a column from the header.
(defn extract-column-index [header column-name]
  (.indexOf header column-name))

;; Calculates the percentage of each post type in the dataset.
(defn calculate-post-type-percentages []
  (let [data @csv-data
        header (first data)]
    (if-let [type-column-index (extract-column-index (first data) post-type)]
      (let [total-posts (count data)
            type-counts (->> data
                             (rest)
                             (map #(nth % type-column-index))
                             (frequencies))]
        (into {}
              (for [[type count] type-counts]
                [type (float (/ (* 100 count) total-posts))])))
      (warn "Column " post-type " can't be found at CSV file."))))

;; Calculates the average reach for each post type.
(defn calculate-average-type-reach []
  (let [data @csv-data
        header (first data)]
    (if-let [type-column-index (extract-column-index header post-type)]
      (let [total-posts (count data)
            type-counts (->> data
                             (rest)
                             (map #(nth % type-column-index))
                             (frequencies))]
        (if-let [reach-column-index (extract-column-index header reach)]
          (let [type-reach (->> data
                                (rest)
                                (map
                                  #(if (< reach-column-index (count %))
                                    (nth % reach-column-index)
                                    nil))
                                (frequencies))]
            (into {}
                  (for [[type count] type-counts
                        :let         [reach-values
                                      (filter #(= type (nth % type-column-index)) data)
                                      valid-reach-values
                                      (remove nil?
                                              (map #(try (Integer/parseInt %) (catch Exception e nil))
                                                   (map #(nth % reach-column-index) reach-values)))
                                      total-reach
                                      (apply + valid-reach-values)]]
                    [type
                     (if (pos? count)
                       (/ total-reach count)
                       0.0)])))))
      (warn "Column " post-type " can't be found at CSV file."))))

;; Calculates the average reach, comments, and likes for each post type.
(defn calculate-engagement-for-type []
  (let [data                  @csv-data
        header                (first data)
        type-column-index     (extract-column-index header post-type)
        reach-column-index    (extract-column-index header reach)
        comments-column-index (extract-column-index header comments-number)
        likes-column-index    (extract-column-index header likes)]
    (if type-column-index
      (let [type-counts (->> data
                             (rest)
                             (map #(nth % type-column-index))
                             (frequencies))]
        (into {}
              (for [[type count] type-counts]
                (let [reach-values (filter #(= type (nth % type-column-index)) data)
                      valid-reach-values (remove nil? (map #(try (Integer/parseInt %) (catch Exception e nil)) (map #(nth % reach-column-index) reach-values)))
                      total-reach (apply + valid-reach-values)
                      total-comments (->> reach-values
                                          (map #(nth % comments-column-index))
                                        (map #(Integer/parseInt %))
                                        (reduce +))
                      total-likes (->> reach-values
                                       (map #(nth % likes-column-index))
                                       (map #(Integer/parseInt %))
                                       (reduce +))]
                  [type
                   {:average-reach    (if (pos? count)
                                        (/ total-reach count)
                                        0.0)
                    :average-comments (if (pos? count)
                                        (/ total-comments count)
                                        0.0)
                    :average-likes    (if (pos? count)
                                        (/ total-likes count)
                                        0.0)}]))))
      (println "Column " post-type " can't be found at CSV file."))))





