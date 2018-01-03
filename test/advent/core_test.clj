(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.core :refer :all]))

(deftest parse-instructions-tests
  (is (= [rect 49 1] (parse-instruction "rect 49x1")))
  (is (= [rotate-column 0 1] (parse-instruction "rotate column x=0 by 1")))
  (is (= [rotate-row 2 34] (parse-instruction "rotate row y=2 by 34"))))

(deftest rect-tests
  (is (= [[1 0] [0 0]] (rect [[0 0] [0 0]] 1 1)))
  (is (= [[1 1 1]
          [1 1 0]
          [1 1 1]
          [0 0 0]] (rect [[0 0 1]
                          [0 1 0]
                          [0 0 1]
                          [0 0 0]] 2 3))))

(deftest rotate-row-tests
  (is (= [[0 0 0 0]
          [0 0 0 1]
          [1 0 1 1]] (rotate-row [[0 0 0 0]
                                  [0 1 0 0]
                                  [1 0 1 1]] 1 2)))
  (is (= [[0 0 0 0]
          [0 1 0 0]
          [1 0 1 1]] (rotate-row [[0 0 0 0]
                                  [0 1 0 0]
                                  [1 1 1 0]] 2 2))))

(deftest rotate-column-tests
  (is (= [[0 1 0 0]
          [0 0 0 0]
          [1 0 1 1]] (rotate-column [[0 0 0 0]
                                     [0 1 0 0]
                                     [1 0 1 1]] 1 2)))
  (is (= [[0 0 0 1]
          [0 1 1 1]
          [1 1 1 1]] (rotate-column [[0 0 1 1]
                                     [0 1 0 1]
                                     [1 1 1 1]] 2 2))))

(deftest integration-example
  (with-redefs [rows 3
                cols 7]
    (let [input ["rect 3x2"
                 "rotate column x=1 by 1"
                 "rotate row y=0 by 4"
                 "rotate column x=1 by 1"]]
      (is (= 6 (solve input))))))
