;; Copyright 2012-present, Polis Technology Inc.
;; All rights reserved.
;; This source code is licensed under the BSD-style license found in the
;; LICENSE file in the root directory of this source tree. An additional grant
;; of patent rights for non-commercial use can be found in the PATENTS file
;; in the same directory.

(ns polismath.pretty-printers
  (:require [clojure.pprint :refer :all]))


(defn str-repeat [s n]
  (apply str (repeat n s)))


(defn prindent [base-indent main-indent & other-strings]
  (print (str-repeat " " (* 2 (+ base-indent main-indent))))
  (apply println other-strings))


(defn print-repness [repness & {:keys [indent] :or {indent 0}}]
  (doseq [[gid comments] repness]
    (prindent indent 0 gid)
    (doseq [c comments]
      (prindent indent 2 c))))


(defn wide-pp [x & {:keys [width] :or {width 400}}]
  (binding [*print-miser-width*  width
            *print-right-margin* width]
    (pprint x)))


