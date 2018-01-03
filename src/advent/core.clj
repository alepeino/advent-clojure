(ns advent.core)

(defn run [instructions]
  (let [reg-or-val (fn [reg x] (or (reg (keyword x)) (Integer. x)))]
    (loop [reg {:a 0 :b 0 :c 1 :d 0}
           i 0]
      (if-let [instruction (get instructions i)]
        (let [[inst x y] (rest (re-find #"(\w+) ([\w-]+)(?: ([\w-]+))?" instruction))]
          (case inst
            "cpy" (recur (assoc reg (keyword y) (reg-or-val reg x))
                         (inc i))
            "inc" (recur (update reg (keyword x) inc)
                         (inc i))
            "dec" (recur (update reg (keyword x) dec)
                         (inc i))
            "jnz" (recur reg
                         (+ i (if (zero? (reg-or-val reg x)) 1 (Integer. y))))))
        reg))))

(defn solve [instructions]
  (:a (run (clojure.string/split-lines instructions))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
