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
    (filter #(clojure.string/starts-with? % "00000"))
    (take 8)
    (map #(nth % 5))
    (apply str)))

(defn input [content]
  content)

(defn solve [input]
  (find-password input))

(defn -main []
  (prn (solve (input (slurp "resources/input.txt")))))
