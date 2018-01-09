(ns advent.core)

(defn swap-pos [r x y]
  (let [x (Integer. x)
        y (Integer. y)]
    (-> (vec r)
      (assoc x (nth r y))
      (assoc y (nth r x)))))

(defn swap-let [r x y]
  (let [x (first x)
        y (first y)]
    (replace {x y y x} r)))

(defn rotate [r dir steps]
  (let [len (count r)
        steps (Integer. steps)
        drop-steps (get {"left" (- (* 2 len) steps)
                         "right" steps}
                        dir 0)]
    (->> (cycle r) (drop drop-steps) (take len))))

(defn rotate-letter [r letter]
  (let [letter (first letter)
        n (->> r (take-while (partial not= letter)) (count))]
    (rotate r "right" (quot (+ n 2 (if (even? n) (count r) 0))
                            2))))

(defn reverse-pos [r x y]
  (let [x (Integer. x)
        y (Integer. y)]
    (concat (take x r)
            (reverse (->> r (drop x) (take (- (inc y) x))))
            (drop (inc y) r))))

(defn move [r x y]
  (let [x (Integer. x)
        y (Integer. y)]
    (->> r
      (keep-indexed #(when (not= y %1) %2))
      (split-at x)
      (#(concat (first %) (->> r (drop y) (take 1)) (second %))))))

(defn solve [input]
  (apply str
    (reduce
      (fn [r s]
        (let [transform #(comp (partial apply % r) rest)]
          (condp re-find s
            #"swap position (\d) with position (\d)" :>> (transform swap-pos)
            #"swap letter (\w) with letter (\w)" :>> (transform swap-let)
            #"rotate (left|right) (\d) steps?" :>> (transform rotate)
            #"rotate based on position of letter (\w)" :>> (transform rotate-letter)
            #"reverse positions (\d) through (\d)" :>> (transform reverse-pos)
            #"move position (\d) to position (\d)" :>> (transform move)
            r)))
      "fbgdceah"
      (reverse (clojure.string/split-lines input)))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
