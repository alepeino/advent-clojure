(ns advent.core)

(defn parse-input [input]
  (->> (clojure.string/split-lines input)
    (map (partial re-find #".+?(\d+) positions.+at position (\d+)."))
    (map (fn [[_ positions start]] {:positions (Integer. positions)
                                    :start (Integer. start)}))))

(defn solve [input]
  (let [discs (parse-input input)
        endpos (map-indexed (fn [index {:keys [positions]}]
                              (->> (dec (- index))
                                (iterate (partial + positions))
                                (drop-while neg?)
                                (first)))
                            discs)]
    (->> discs
      (map #(->> (range (:positions %)) (cycle) (drop (:start %))))
      (apply map vector)
      (keep-indexed #(when (#{endpos} %2) %1))
      (first))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
