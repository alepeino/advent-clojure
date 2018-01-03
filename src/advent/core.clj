(ns advent.core
  (:require [clojure.core.matrix :as m]))

(defn solve [input]
  (loop [elves (m/array :vectorz (range 1 (inc (Integer. input))))]
    (let [ecount (m/ecount elves)]
      (if (= 1 ecount)
        (-> elves first int)
        (let [target (quot ecount 2)]
          (recur (m/join (m/subvector elves 1 (dec target))
                         (m/subvector elves (inc target) (- ecount target 1))
                         (m/subvector elves 0 1))))))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
