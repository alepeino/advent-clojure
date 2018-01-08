(ns advent.core)

(defn parse-ranges [input]
  (->> input
    (clojure.string/split-lines)
    (map #(clojure.string/split % #"-"))
    (map #(map (fn [n] (BigInteger. n)) %))
    (sort-by first)))

(defn solve [input]
  (let [allowed (BigInteger. "4294967296")]
    (loop [[[range-lo range-hi] :as ranges] (parse-ranges input)
           max-banned (BigInteger. "-1")
           banned 0]
      (if-not ranges
        (- allowed banned)
        (recur (next ranges)
               (max range-hi max-banned)
               (+ banned
                  (- (max max-banned range-hi)
                     (max max-banned (dec range-lo)))))))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
