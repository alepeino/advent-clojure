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
    (if-let [path (->> paths
                    (remove (comp #{finish} val))
                    (map key)
                    (remove tried)
                    (first))]
      (recur (reduce (fn [m [dir loc]]
                       (assoc m (str path dir) loc))
                     paths
                     (next-open passcode path (paths path)))
             (conj tried path))
      (->> paths
        (filter (comp #{finish} val))
        (map (comp count key))
        sort reverse first))))

(defn solve [input]
  (shortest [0 0] [3 3] input))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
