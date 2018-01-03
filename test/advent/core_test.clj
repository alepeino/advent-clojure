(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.core :refer :all]))

(deftest move-tests
  (testing "Move right"
    (is (= [1 2] (move [1 1] \R))))
  (testing "Move right, capped"
    (is (= [2 2] (move [2 2] \R))))
  (testing "Move up"
    (is (= [0 1] (move [1 1] \U))))
  (testing "Move up, capped"
    (is (= [0 0] (move [0 0] \U)))))

(deftest move-line-tests
  (testing "Apply all movements in a line"
    (is (= [0 0] (move-line [1 1] "ULL")))
    (is (= [2 2] (move-line [0 0] "RRDDD")))
    (is (= [2 1] (move-line [2 2] "LURDL")))
    (is (= [1 1] (move-line [2 1] "UUUUD")))))

(deftest solve-tests
  (testing ""
    (is (= [1 9 8 5] (solve ["ULL" "RRDDD" "LURDL" "UUUUD"])))))

(deftest digit-from-pos-tests
  (testing ""
    (is (= 3 (digit-from-pos [0 2])))
    (is (= 4 (digit-from-pos [1 0])))
    (is (= 8 (digit-from-pos [2 1])))))
