(ns advent.core)

(defn md5 [s]
  (->> (.getBytes s)
    (.digest (java.security.MessageDigest/getInstance "MD5"))
    (java.math.BigInteger. 1)
    (format "%032x")))

(defn solve [salt]
  (let [hasher (memoize md5)
        hashes (map (comp hasher (partial str salt)) (range))]
    (->> (range)
      (filter (fn [n]
                (when-let [[_ c] (re-find #".*?(.)\1\1.*" (nth hashes n))]
                  (let [re (re-pattern (apply str (repeat 5 c)))]
                    (when (some (partial re-find re)
                                (->> hashes (drop (inc n)) (take 1000)))
                      n)))))
      (take 64)
      (last))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
