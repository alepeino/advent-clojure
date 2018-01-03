(ns advent.core)

(defn input [strs]
  (->> strs
    (clojure.string/split-lines)
    (map clojure.string/trim)))

(defn solve [input]
  (->> input
    (apply map str)
    (map frequencies)
    (map (partial sort-by second))
    (map (comp first last))
    (apply str)))

(defn -main []
  (prn (solve (input (slurp "resources/input.txt")))))
