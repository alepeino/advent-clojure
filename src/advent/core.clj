(ns advent.core)

(defn marker [length times after]
  (->> after
    (split-at (Integer. length))
    ((juxt (comp flatten (partial repeat (Integer. times)) first)
           second))
    (map (partial apply str))))

(defn parse-one [s]
  (condp re-find s
    #"^\((\d+)x(\d+)\)(.*)" :>> (comp (partial apply marker) rest)
    #"^(.+?)(\(.*)" :>> rest
    (list s)))

(defn decompress [s]
  (let [[one rst] (parse-one s)]
    (if (empty? rst)
      one
      (str one (decompress rst)))))

(defn solve [input]
  (count (decompress input)))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
