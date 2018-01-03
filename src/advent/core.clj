(ns advent.core
  (:import (java.io File FileWriter)))

(defn has-marker? [s]
  (re-find #"\(\d+x\d+\)" s))

(defn process-marker [length-str times-str after]
  [(->> (subs after 0 (Integer. length-str))
     (repeat (Integer. times-str))
     (apply str))
   (+ (Integer. length-str)
      (count (str "(" length-str "x" times-str ")")))])

(defn process-one [s]
  (condp re-find s
    #"^\((\d+)x(\d+)\)(.*)" :>> (comp (partial apply process-marker) rest)
    #"^(.+?)\(\d+x\d+\).*" :>> (juxt second (comp count second))
    [s (count s)]))

(defn process-all [input on-progress]
  (loop [data input]
    (let [[text consumed] (process-one data)]
      (if (has-marker? text)
        (process-all text on-progress)
        (on-progress text))
      (when (> (count data) consumed)
        (recur (subs data consumed))))))

(defn solve [input]
  ;;; Will write decompressed data (~10GB) to file!
  (with-open [writer (FileWriter. "resources/output.txt")]
    (process-all input #(doto writer (.write %) (.flush))))
  (.length (File. "resources/output.txt")))

  ;;; Will not generate decompressed output, only get its size
  ;(let [counter (atom (BigInteger. "0"))]
  ;  (process-all input (fn [s] (swap! counter #(.add % (->> s count str BigInteger.)))))
  ;  @counter)

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
