(ns advent.core)

(declare add-chip)
(declare remove-chip)

(defn behave [bots id]
  (let [{:keys [behaviour chips]} (get bots id)]
    (if (and behaviour (= 2 (count chips)))
      (let [min-chip (apply min chips)
            max-chip (apply max chips)]
        (-> bots
          (update :log conj (str id " comparing chip " min-chip " and chip " max-chip))
          (add-chip min-chip (:lo behaviour))
          (remove-chip min-chip id)
          (add-chip max-chip (:hi behaviour))
          (remove-chip max-chip id)))
      bots)))

(defn set-behaviour [bots id lo hi]
  (-> bots
    (assoc-in [id :behaviour] {:lo lo :hi hi})
    (behave id)))

(defn add-chip [bots chip id]
  (-> bots
    (update-in [id :chips] (fnil conj #{}) (Integer. chip))
    (behave id)))

(defn remove-chip [bots chip id]
  (update-in bots [id :chips] disj (Integer. chip)))

(defn process [instructions]
  (let [apply-instruction #(comp (partial apply %1 %2) rest)]
    (reduce
      (fn [ret instruction]
        (condp re-find instruction
          #"value (\d+) goes to (bot \d+)" :>> (apply-instruction add-chip ret)
          #"(bot \d+) gives low to (.+) and high to (.+)" :>> (apply-instruction set-behaviour ret)
          ret))
      {}
      instructions)))

(defn solve [input]
  (let [state (process (clojure.string/split-lines input))]
    (->> (:log state)
      (keep #(re-find #"bot (\d+) comparing chip 17 and chip 61" %))
      (first)
      (second))))

(defn -main []
  (println (solve (slurp "resources/input.txt"))))
