(ns advent.core)

(defn dragon [a]
  (apply str a \0 (map {\0 \1 \1 \0} (reverse a))))

(defn checksum [s]
  (if (odd? (count s))
    s
    (->> s
      (partition 2)
      (map (partial apply distinct?))
      (map {false \1 true \0})
      (apply str)
      (checksum))))

(defn solve [input]
  (let [len 272]
    (->> (iterate dragon input)
      (drop-while (comp (partial > len) count))
      (first)
      (take len)
      (checksum))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
