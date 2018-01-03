(ns advent.core)

(defn rotate-char [ch steps]
  (as-> "abcdefghijklmnopqrstuvwxyz" $
    (cycle $)
    (drop-while (partial not= ch) $)
    (nth $ steps)))

(defn rotate-str [st steps]
  (apply str (map #(rotate-char % steps) st)))

(defn input [content]
  (->> content
    (clojure.string/split-lines)
    (map #(rest (re-find #"(.+?)(\d+)\[(.+)\]" %)))
    (map (fn [[n i c]]
           [(->> n (remove #{\-}) (apply str))
            (Integer. i)
            c]))))

(defn solve [input]
  (some (fn [[n i _]]
          (when (#{"northpoleobjectstorage"}  (rotate-str n i))
            i))
        input))

(defn -main []
  (prn (solve (input (slurp "resources/input.txt")))))
