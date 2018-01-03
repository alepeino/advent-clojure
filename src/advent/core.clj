(ns advent.core)

(defn real? [name _ checksum]
  (->> name
    (frequencies)
    (sort-by second)
    (reverse)
    (partition-by second)
    (mapcat (partial sort-by first))
    (map first)
    (take 5)
    (apply str)
    (= checksum)))

(defn input [content]
  (->> content
    (clojure.string/split-lines)
    (map #(rest (re-find #"(.+?)(\d+)\[(.+)\]" %)))
    (map (fn [[n i c]]
           [(->> n (remove #{\-}) (apply str))
            (Integer. i)
            c]))))

(defn solve [input]
  (->> input
    (filter (partial apply real?))
    (map second)
    (apply +)))

(defn -main []
  (prn (solve (input (slurp "resources/input.txt")))))
