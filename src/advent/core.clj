(ns advent.core)

(def dim 5)

(defn valid-space? [coords]
  (or
    (some #{2} coords)
    (every? #{1 3} coords)))

(defn constrain [n lower upper]
  (cond
    (< n lower) lower
    (> n upper) upper
    :else n))

(defn move [[r0 c0] dir]
  (let [[r1 c1] ({\L [0 -1]
                  \R [0 1]
                  \U [-1 0]
                  \D [1 0]}
                 dir)
        new-pos [(constrain (+ r0 r1) 0 (dec dim))
                 (constrain (+ c0 c1) 0 (dec dim))]]
    (if (valid-space? new-pos)
      new-pos
      [r0 c0])))

(defn move-line [from line-cmd]
  (reduce move from line-cmd))

(defn char-from-pos [pos]
  (let [spaces (for [x (range 5) y (range 5)
                     :when (valid-space? [x y])]
                 [x y])]
    (->> pos
      (.indexOf spaces)
      (inc)
      (format "%X")
      (first))))

(defn solve [input]
  (map char-from-pos
    (loop [result []
           pos [2 0]
           [line & more] input]
      (if line
        (let [new-pos (move-line pos line)]
          (recur (conj result new-pos) new-pos more))
        result))))

(def input
  (-> "resources/input.txt"
    (slurp)
    (clojure.string/split-lines)))

(defn -main []
  (prn (solve input)))
