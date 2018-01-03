(ns advent.core-test
  (:require [clojure.test :refer :all]
            [advent.core :refer :all]))

(deftest real?-tests
  (testing ""
    (is (real? "aaaaabbbzyx" 123 "abxyz"))
    (is (real? "abcdefgh" 987 "abcde"))
    (is (real? "notarealroom" 404 "oarel"))
    (is (not (real? "totallyrealroom" 200 "decoy")))))

(deftest solve-test
  (testing ""
    (is (= 1514 (solve (input (str "aaaaa-bbb-z-y-x-123[abxyz]\n"
                                   "a-b-c-d-e-f-g-h-987[abcde]\n"
                                   "not-a-real-room-404[oarel]\n"
                                   "totally-real-room-200[decoy]\n")))))))
