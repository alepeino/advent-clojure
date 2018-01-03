(ns advent.core)

(defn md5 [s]
  (->> (.getBytes s)
    (.digest (java.security.MessageDigest/getInstance "MD5"))
    (java.math.BigInteger. 1)
    (format "%032x")))

(defn next-open [passcode path [x y]]
  (let [hash (md5 (apply str passcode path))
        [U D L R] (map #{\b \c \d \e \f} (take 4 hash))]
    (cond-> {}
      (and U (> x 0)) (assoc \U [(dec x) y])
      (and D (< x 3)) (assoc \D [(inc x) y])
      (and L (> y 0)) (assoc \L [x (dec y)])
      (and R (< y 3)) (assoc \R [x (inc y)]))))

(defn shortest [start finish passcode]
  (loop [paths {"" start}
         tried #{}]
    (let [[path] (->> (keys paths) (remove tried) (sort-by count))
          paths' (reduce (fn [m [dir loc]]
                           (assoc m (str path dir) loc))
                         paths
                         (next-open passcode path (paths path)))]
      (or (->> paths' (filter (comp #{finish} val)) (keep key) (first))
          (recur paths' (conj tried path))))))

(defn solve [input]
  (shortest [0 0] [3 3] input))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
