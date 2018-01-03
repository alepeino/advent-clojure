(ns advent.core)

(defn distance-to-center [{[x y] :pos}]
  (+ (Math/abs x)
     (Math/abs y)))

(defn step-increment [dir]
  (case dir
    0 [0 1]
    1 [1 0]
    2 [0 -1]
    3 [-1 0]))

(defn new-pos [current dir steps]
  (map +
    current
    (map * [steps steps] (step-increment dir))))

(defn new-dir [current turn]
  (mod (({\L dec \R inc} turn) current)
       4))

(defn move [{:keys [dir pos]} [turn & steps]]
  (let [steps (Integer. (apply str steps))
        new-dir (new-dir dir turn)
        new-pos (new-pos pos new-dir steps)]
    {:dir new-dir
     :pos new-pos}))

(defn solve [input]
  (distance-to-center
    (reduce
      move
      {:dir 0 :pos [0 0]}
      input)))

(defn -main []
  (let [input (-> "resources/input.txt"
                  (slurp)
                  (clojure.string/split #", "))]
    (prn (solve input))))
