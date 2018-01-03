(ns advent.core)

(def rows 6)
(def cols 50)

(defn initial []
  (repeat rows (repeat cols 0)))

(defn- rotate [coll by]
  (let [size (count coll)]
    (->> (cycle coll)
      (drop (- size by))
      (take size))))

(defn rotate-row [screen n by]
  (let [[left [row & right]] (split-at n screen)]
    (concat left [(rotate row by)] right)))

(defn rotate-column [screen n by]
  (let [transpose (partial apply map vector)]
    (-> screen
      (transpose)
      (rotate-row n by)
      (transpose))))

(defn rect [screen x y]
  (map (partial map bit-or)
       screen
       (concat (repeat y (concat (repeat x 1) (repeat 0)))
               (repeat (repeat 0)))))

(defn parse-instruction [s]
  (let [result (fn [f args] (into [f] (map #(Integer. %) args)))]
    (condp re-find s
      #"rect (\d+)x(\d+)" :>> (fn [[_ & args]] (result rect args))
      #"rotate column x=(\d+) by (\d+)" :>> (fn [[_ & args]] (result rotate-column args))
      #"rotate row y=(\d+) by (\d+)" :>> (fn [[_ & args]] (result rotate-row args)))))

(defn apply-instructions [instructions]
  (reduce
    (fn [screen [f & args]]
      (apply f screen args))
    (initial)
    (map parse-instruction instructions)))

(defn input [strs]
  (->> strs
    (clojure.string/split-lines)
    (map clojure.string/trim)))

(defn solve [input]
  (reduce +
    (map (partial reduce +) (apply-instructions input))))

(defn -main []
  (prn (solve (input (slurp "resources/input.txt")))))
