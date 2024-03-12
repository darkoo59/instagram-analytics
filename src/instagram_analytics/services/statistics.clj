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
    (if-let [type-column-index (.indexOf header "Тип објаве")]
      (let [total-posts (count data)
            type-counts (->> data
                             (rest)
                             (map #(nth % type-column-index))
                             (frequencies))]
        (if-let [reach-column-index (.indexOf header "Домет")]
          (let[
            type-reach (->> data
                            (rest)
                            (map #(if (< reach-column-index (count %))
                                   (nth % reach-column-index)
                                   nil))
                            (frequencies))]
            (into {}
                  (for [[type count] type-counts
                        :let [reach-values (filter #(= type (nth % type-column-index)) data)
                              valid-reach-values (remove nil? (map #(try (Integer/parseInt %) (catch Exception e nil)) (map #(nth % reach-column-index) reach-values)))
                              total-reach (apply + valid-reach-values)]]
                    [type (if (pos? count)
                            (/ total-reach count)
                            0.0)])))))

      (println "Kolona 'Тип објаве' nije pronadjena u CSV fajlu."))))

(defn extract-column-index [header column-name]
  (.indexOf header column-name))

(defn calculate-engagement-for-type []
  (let [data @csv-data
        header (first data)
        type-column-index (extract-column-index header "Тип објаве")
        reach-column-index (extract-column-index header "Домет")
        comments-column-index (extract-column-index header "Број коментара")
        likes-column-index (extract-column-index header "Свиђања")]
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
                  [type {:average-reach (if (pos? count)
                                          (/ total-reach count)
                                          0.0)
                         :average-comments (if (pos? count)
                                             (/ total-comments count)
                                             0.0)
                         :average-likes (if (pos? count)
                                          (/ total-likes count)
                                          0.0)}]))))
    (println "Kolona 'Тип објаве' nije pronadjena u CSV fajlu."))))





