(ns advent.core)

(defn turn [data turn-dir]
  (let [turn-fn ({\L dec \R inc} turn-dir)]
    (update data :dir #(-> % turn-fn (mod 4)))))

(defn step-increment [dir]
  (case dir
    0 [0 1]
    1 [1 0]
    2 [0 -1]
    3 [-1 0]))

(defn walk [data steps]
  (let [increment (step-increment (:dir data))]
    (reduce
      (fn [{:keys [visited] :as data-r}  _]
        (let [new-loc (map + (last visited) increment)
              visited (conj visited new-loc)
              new-data (assoc data-r :visited visited)]
          (if (apply distinct? visited)
            new-data
            (reduced (assoc new-data :end true)))))
      data
      (range steps))))

(defn move [data [turn-dir & steps]]
  (let [steps (Integer. (apply str steps))
        turned (turn data turn-dir)]
    (walk turned steps)))

(defn distance-to-center [data]
  (let [[x y] (last (:visited data))]
    (+ (Math/abs x)
       (Math/abs y))))

(defn solve [input]
  (distance-to-center
    (reduce
      (fn [data movement]
        (let [new-data (move data movement)]
          (if (:end new-data)
            (reduced new-data)
            new-data)))
      {:dir 0 :visited [[0 0]]}
      input)))

(defn -main []
  (let [input (-> "resources/input.txt"
                  (slurp)
                  (clojure.string/split #", "))]
    (prn (solve input))))
