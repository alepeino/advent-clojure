(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.core :refer :all]))

(deftest decompress-tests
  (testing "Marker within decompressed data is re-processed"
    (is (= 20 (solve "X(8x2)(3x3)ABCY")))
    (is (= 241920 (solve "(27x12)(20x12)(13x14)(7x10)(1x12)A")))
    (is (= 445 (solve "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")))))
