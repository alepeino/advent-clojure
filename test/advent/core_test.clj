(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.core :refer :all]))

(deftest rotate-char-tests
  (testing ""
    (is (= \b (rotate-char \a 1)))
    (is (= \m (rotate-char \c 10)))
    (is (= \c (rotate-char \x 5)))
    (is (= \j (rotate-char \j 0)))
    (is (= \h (rotate-char \h 26)))
    (is (= \r (rotate-char \q 2601)))))

(deftest rotate-str-tests
  (testing ""
    (is (= "bcd" (rotate-str "abc" 1)))
    (is (= "mn" (rotate-str "cd" 10)))
    (is (= "t" (rotate-str "t" 0)))
    (is (= "rst" (rotate-str "qrs" 2601)))))
