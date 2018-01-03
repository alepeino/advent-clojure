(ns advent.core)

(defn input [strs]
  (->> strs
    (clojure.string/split-lines)
    (map clojure.string/trim)))

(defn has-abba? [s]
  (let [[a b b2 a2 & more] s]
    (or (and (not= a b)
             (= a a2)
             (= b b2))
        (when (seq more)
	  (has-abba? (next s))))))

(defn supports-tls? [s]
  (->> s
    (partition-by #{\[ \]})
    (take-nth 2)
    ((juxt (partial take-nth 2)
           (comp (partial take-nth 2) rest)))
    ((fn [[out in]]
      (and (some has-abba? out)
           (not-any? has-abba? in))))))

(defn solve [input]
  (->> input
    (filter supports-tls?)
    (count)))

(defn -main []
  (prn (solve (input (slurp "resources/input.txt")))))
