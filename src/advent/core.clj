(ns advent.core)

(def coords (juxt :x :y))

(defn parse-input [input]
  (->> (clojure.string/split-lines input)
    (drop 2)
    (map (partial re-find #"node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T"))
    (map rest)
    (map (partial map #(Integer. %)))
    (map (partial zipmap [:x :y :size :used]))
    (map (juxt coords identity))
    (into {})))

(defn viable-pair? [d1 d2]
  (and (pos? (:used d1))
       (not= (coords d1) (coords d2))
       (or (and (= (:x d1) (:x d2))
                (= 1 (Math/abs (- (:y d1) (:y d2)))))
           (and (= (:y d1) (:y d2))
                (= 1 (Math/abs (- (:x d1) (:x d2))))))
       (<= (:used d1) (- (:size d2) (:used d2)))))

(defn viable-pairs [disks]
  (->> (for [[_ d1] disks [_ d2] disks] [d1 d2])
    (filter (partial apply viable-pair?))))

(defn transfer [m d1 d2]
  (-> m
    (assoc-in [(coords d1) :target] nil)
    (assoc-in [(coords d1) :used] 0)
    (assoc-in [(coords d2) :target] (:target d1))
    (update-in [(coords d2) :used] + (:used d1))))

;; General solution; takes forever
(defn solve [input]
  (let [disks (parse-input input)
        target [(apply max (map (comp :x val) disks))
                (apply min (map (comp :y val) disks))]
        disks (assoc-in disks [target :target] true)]
    (loop [moves {disks 0}
           tried #{}]
      (let [[[current steps]] (->> moves
                                (remove (comp tried key))
                                (sort-by val))]
        (if (get-in current [[0 0] :target])
          steps
          (recur (reduce (fn [m [d1 d2]]
                           (assoc m (transfer current d1 d2) (inc steps)))
                         moves
                         (viable-pairs current))
                 (conj tried current)))))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
