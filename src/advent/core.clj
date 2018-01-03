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
  (count (filter valid? input)))

(defn -main []
  (prn (solve input)))
