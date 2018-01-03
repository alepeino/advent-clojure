(ns advent.core)

(defn- open? [input [x y]]
  (->> (+ (* x x)
          (* 3 x)
          (* 2 x y)
          y
          (* y y)
          (Integer. input))
    (Integer/toBinaryString)
    (filter #{\1})
    (count)
    (even?)))

(defn- adjacents [[x y]]
  (cond-> [[x (inc y)] [(inc x) y]]
    (pos? y) (conj [x (dec y)])
    (pos? x) (conj [(dec x) y])))

(defn locations [start steps input]
  (let [open? (partial open? input)]
    (loop [distances {start 0}
           visited #{}]
      (let [[[current]] (->> distances
                          (remove (comp visited first))
                          (sort-by second))
            distances' (reduce (fn [m node]
                                (let [d (inc (m current))]
                                  (update m node #(if % (min % d) d))))
                               distances
                               (filter open? (adjacents current)))]
        (if (> (distances' current) steps)
          (count (filter (comp (partial >= steps) second) distances'))
          (recur distances' (conj visited current)))))))

(defn solve [input]
  (locations [1 1] 50 input))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
