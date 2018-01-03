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

(defn shortest [start finish input]
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
        (or (distances' finish)
            (recur distances' (conj visited current)))))))

(defn solve [input]
  (shortest [1 1] [31 39] input))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
