(ns advent.core)

(defn md5
  "MD5 hash (https://gist.github.com/jizhang/4325757)"
  [s]
  (let [algorithm (java.security.MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (java.math.BigInteger. 1 raw))))

(defn find-password [id]
  (->> (range)
    (map (partial str id))
    (map md5)
    (filter (partial re-matches #"00000[0-7].+"))
    (map #(subs % 5 7))
    (reduce (fn [r [pos ch]]
              (let [r2 (update r pos (fnil identity ch))]
                (if (>= (count r2) 8) (reduced r2) r2)))
            {})
    (sort-by first)
    (map second)
    (apply str)))

(defn input [content]
  content)

(defn solve [input]
  (find-password input))

(defn -main []
  (prn (solve (input (slurp "resources/input.txt")))))
