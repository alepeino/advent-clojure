(ns advent.core)

(defn valid? [sides]
  (let [perimeter (apply + sides)]
    (every? #(< % (/ perimeter 2)) sides)))

(def input
  (->> "resources/input.txt"
    (slurp)
    (clojure.string/split-lines)
    (map #(as-> % $
           (clojure.string/trim $)
           (clojure.string/split $ #"\s+")
           (map (fn [s] (Integer. s)) $)))))

(defn solve [input]
  (let [input (->> input (apply interleave) (partition 3))]
    (count (filter valid? input))))

(defn -main []
  (prn (solve input)))
