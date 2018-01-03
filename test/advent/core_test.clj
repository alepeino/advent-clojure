(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.core :refer :all]))

(deftest valid-spaces-test
  (testing "middle row and column are valid"
    (is (valid-space? [2 2]))
    (is (valid-space? [0 2]))
    (is (valid-space? [2 1])))
  (testing "remaining: 2-4-A-C"
    (is (valid-space? [1 1]))
    (is (valid-space? [1 3]))
    (is (valid-space? [3 1]))
    (is (valid-space? [3 3])))
  (testing "not valid"
    (is (not (valid-space? [1 4])))
    (is (not (valid-space? [4 3])))
    (is (not (valid-space? [0 1])))))

(deftest move-tests
  (testing "Move right"
    (is (= [1 2] (move [1 1] \R))))
  (testing "Move right, capped"
    (is (= [2 4] (move [2 4] \R))))
  (testing "Move up"
    (is (= [1 1] (move [2 1] \U))))
  (testing "Move up, capped"
    (is (= [0 2] (move [0 2] \U))))
  (testing "Can't move right from '1'"
    (is (= [0 2] (move [0 2] \R))))
  (testing "Can't move up from '9'"
    (is (= [0 0] (move [0 0] \U)))))

(deftest move-line-tests
  (testing "Apply all movements in a line"
    (is (= [2 0] (move-line [2 0] "ULL")))
    (is (= [4 2] (move-line [2 0] "RRDDD")))
    (is (= [3 2] (move-line [4 2] "LURDL")))
    (is (= [1 2] (move-line [3 2] "UUUUD")))))

(deftest solve-tests
  (testing ""
    (is (= [\5 \D \B \3] (solve ["ULL" "RRDDD" "LURDL" "UUUUD"])))))

(deftest char-from-pos-tests
  (testing ""
    (is (= \1 (char-from-pos [0 2])))
    (is (= \6 (char-from-pos [2 1])))
    (is (= \B (char-from-pos [3 2])))))
