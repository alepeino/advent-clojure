(ns advent.core
  (:import java.io.File))

(defn dragon [file limit]
  (let [size (.length (File. file))
        needed (- limit size)]
    (when pos? needed
      (let [s (slurp file)
            append (->> s
                     (drop (max (inc (- size needed)) 0))
                     (map {\0 \1 \1 \0})
                     (reverse)
                     (apply str \0))]
        (spit file append :append true)
        (when (< (count append) needed)
          (recur file limit))))))

(defn checksum [s]
  (if (odd? (count s))
    s
    (->> s
      (partition 2)
      (map (partial apply distinct?))
      (map {false \1 true \0})
      (apply str)
      (checksum))))

(defn solve [input]
  (let [len 35651584
        file "resources/disk"]
    (spit file input)
    (dragon file len)
    (checksum (slurp file))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
