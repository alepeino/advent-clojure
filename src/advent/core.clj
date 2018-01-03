(ns advent.core)

(defn pass [elves]
  (if (even? (count elves))
    (take-nth 2 elves)
    (cons (last elves) (pass (butlast elves)))))

(defn solve [input]
  (->> (range 1 (inc (Integer. input)))
    (iterate pass)
    (drop-while #(> (count %) 1))
    (first)
    (first)))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
