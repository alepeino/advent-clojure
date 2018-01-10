(ns advent.core)

(defn parse-input [input]
  (->> (clojure.string/split-lines input)
    (drop 2)
    (map (partial re-find #"node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T"))
    (map rest)
    (map (partial map #(Integer. %)))
    (map (partial zipmap [:x :y :size :used]))))

(defn viable-pair? [d1 d2]
  (and (pos? (:used d1))
       (not= (select-keys d1 [:x :y])
             (select-keys d2 [:x :y]))
       (<= (:used d1) (- (:size d2) (:used d2)))))

(defn solve [input]
  (let [disks (parse-input input)]
    (->> (for [d1 disks d2 disks] [d1 d2])
      (filter (partial apply viable-pair?))
      (count))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
