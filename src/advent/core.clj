(ns advent.core
   (:require [clojure.core.match :refer [match]]))

(defn get-tile [previous-row]
  (match (vec previous-row)
    [\^ \^ \.] \^
    [\. \^ \^] \^
    [\^ \. \.] \^
    [\. \. \^] \^
    :else \.))

(defn next-row [row]
  (->> (concat [\.] row [\.])
    (partition 3 1)
    (map get-tile)))

(defn solve [input]
  (->> input
    (iterate next-row)
    (take 40)
    (mapcat (partial keep #{\.}))
    (count)))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
