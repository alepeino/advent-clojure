(ns advent.core)

;; (?) https://www.reddit.com/r/adventofcode/comments/5hoia9/2016_day_11_solutions/db27z3h/
(defn solve [initial]
  (->> initial
    (butlast)
    (reverse)
    (iterate next)
    (take-while some?)
    (map #(apply + -3 (map (partial * 2) %)))
    (apply +)))

(defn -main []
  (println (solve [4 5 1 0])))
