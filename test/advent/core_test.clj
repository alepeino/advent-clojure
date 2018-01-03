(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.core :refer :all]))

(deftest decompress-tests
  (testing "No markers"
    (is (= "ADVENT" (decompress "ADVENT"))))
  (testing "Text before marker"
    (is (= "ABBBBBC" (decompress "A(1x5)BC"))))
  (testing "Marker at the beginning"
    (is (= "XYZXYZXYZ" (decompress "(3x3)XYZ"))))
  (testing "Multiple markers"
    (is (= "ABCBCDEFEFG" (decompress "A(2x2)BCD(2x2)EFG"))))
  (testing "Marker within characters affected by first marker is not processed"
    (is (= "(1x3)A" (decompress "(6x1)(1x3)A"))))
  (testing "Marker within characters affected by first marker is repeated normally"
    (is (= "X(3x3)ABC(3x3)ABCY" (decompress "X(8x2)(3x3)ABCY")))))
