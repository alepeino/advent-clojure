(ns advent.core)

(def rows 3)
(def cols 3)

(defn constrain [n lower upper]
  (cond
    (< n lower) lower
    (> n upper) upper
    :else n))

(defn digit-from-pos [[r c]]
  (+ (* r cols) c 1))

(defn move [[r0 c0] dir]
  (let [[r1 c1] ({\L [0 -1]
                  \R [0 1]
                  \U [-1 0]
                  \D [1 0]}
                 dir)]
    [(constrain (+ r0 r1) 0 (dec rows))
     (constrain (+ c0 c1) 0 (dec cols))]))

(defn move-line [from line-cmd]
  (reduce move from line-cmd))

(defn solve [input]
  (map digit-from-pos
    (loop [result []
           pos [1 1]
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
