(ns advent.core
  (:require [clojure.set]))

(defn input [strs]
  (->> strs
    (clojure.string/split-lines)
    (map clojure.string/trim)))

(defn aba-and-bab? [out in]
  (let [aba (fn [[a b a2]] (when (and (= a a2) (not= a b)) (seq [a b a2])))
        aba->bab (fn [[a b]] (seq [b a b]))
        get-abas #(->> % (iterate rest) (take-while seq) (keep aba))]
    (seq (clojure.set/intersection
           (->> out (mapcat get-abas) (set))
           (->> in (mapcat get-abas) (map aba->bab) (set))))))

(defn supports-ssl? [s]
  (->> s
    (partition-by #{\[ \]})
    (take-nth 2)
    ((juxt (partial take-nth 2)
           (comp (partial take-nth 2) rest)))
    (apply aba-and-bab?)))

(defn solve [input]
  (->> input
    (filter supports-ssl?)
    (count)))

(defn -main []
  (prn (solve (input (slurp "resources/input.txt")))))
