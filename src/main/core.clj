(ns main.core
  (:gen-class)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn load-csv [resource-path]
  (let [resource-url (io/resource resource-path)]
    (with-open [reader (io/reader (.getFile resource-url))]
      (doall (csv/read-csv reader)))))

(defn top-n-posts [data n column-name]
  (let [header (first data)
        likes-column-name "Свиђања"
        saves-column-name "Чувања"]
    (if-let [column-index (.indexOf header column-name)]
      (->> data
           (rest)
           (map (fn [row] {:id-objave (nth row 0) :count (Integer. (nth row column-index))}))
           (sort-by :count >)
           (take n))
      (println (str "Kolona \"" column-name "\" nije pronađena u CSV fajlu.")))))

(defn -main []
  (println "Dobrodošli u analizu Instagram podataka!")
  (let [file-path "last_month.csv"]
    (def csv-data (load-csv file-path))
    (println "Izaberite vrstu analize:")
    (println "1 - Top n najlajkovanijih objava")
    (println "2 - Top n objava po broju čuvanja")
    (let [choice (read)]
      (cond
        (= choice 1)
        (do
          (println "Unesite broj n za računanje top n najlajkovanijih objava:")
          (let [n (read)]
            (let [top-n (top-n-posts csv-data n "Свиђања")]
              (println (str "Top " n " najlajkovanijih objava:"))
              (doseq [post top-n]
                (println (str "ID objave: " (:id-objave post) ", Broj lajkova: " (:count post))))))
          )

        (= choice 2)
        (do
          (println "Unesite broj n za izračun top n objava po broju čuvanja:")
          (let [n (read)]
            (let [top-n (top-n-posts csv-data n "Чувања")]
              (println (str "Top " n " objava po broju čuvanja:"))
              (doseq [post top-n]
                (println (str "ID objave: " (:id-objave post) ", Broj čuvanja: " (:count post))))))
          )

        :else
        (println "Nevažeći izbor.")
        )
      )
    ))