(ns advent.core)

(defn parse-ranges [input]
  (->> input
    (clojure.string/split-lines)
    (map #(clojure.string/split % #"-"))
    (map #(map (fn [n] (BigInteger. n)) %))
    (sort-by first)))

(defn solve [input]
  (loop [[[range-lo range-hi] & more] (parse-ranges input)
         current-ip (BigInteger. "0")]
    (if (>= current-ip range-lo)
      (recur more (inc range-hi))
      current-ip)))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
