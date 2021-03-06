;; Copyright 2012-present, Polis Technology Inc.
;; All rights reserved.
;; This source code is licensed under the BSD-style license found in the
;; LICENSE file in the root directory of this source tree. An additional grant
;; of patent rights for non-commercial use can be found in the PATENTS file
;; in the same directory.

;; gorilla-repl.fileformat = 1

;; **
;;; # Performance monitoring
;;; 
;;; We've begun puttng graph profile information into mongo. Time to start working on some monitoring tools.
;; **

;; @@
(ns performance-monitoring
  (:require [gorilla-plot.core :as plot]
            [gorilla-repl.table :as table]
            [polismath.utils :refer :all]
            [polismath.db :as db]
            [polismath.env :as env]
            [monger.core :as mg]
            [monger.collection :as mc]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Below are the various plotting things we've done so far:
;; **

;; @@
(defn stat-plot
  [attr & opts]
  (apply plot/list-plot (map attr gp1-rep-stats) opts))

(defn stat-plots
  [attrs colors]
  (apply plot/compose (map #(stat-plot %1 :color %2 :width 500) attrs colors)))

(stat-plots [:pat :pdt] ["steelblue" "red"])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;performance-monitoring/stat-plots</span>","value":"#'performance-monitoring/stat-plots"}
;; <=

;; @@
(defn plot-attrs
  [users x-attr y-attr & {:keys [] :as kw-args}]
  (let [plot-args (merge {:opacity 0.3
                          :symbol-size 20}
                         kw-args)]
    (apply-kwargs
      plot/list-plot
      (map
        #(gets % [x-attr y-attr])
        users)
      plot-args)))


(plot-attrs icusers :remote-created-at :n-owned-convs-ptptd
            :plot-range [:all [0 20]])
;; @@

;; **
;;; Now let's start loading up some data to play around with.
;;; 
;; **

;; @@
(defn load-conv-profiles
  "Very bare bones reloading of the conversation; no cleanup for keyword/int hash-map key mismatches,
  as found in the :repness"
  [zid]
  (mc/find-one-as-map
    (db/mongo-db (env/env :mongolab-uri))
    (db/mongo-collection-name "profile")
    {:zid zid}))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;performance-monitoring/load-conv-profiles</span>","value":"#'performance-monitoring/load-conv-profiles"}
;; <=

;; @@
(load-conv-profiles 11586)
;; @@

;; @@
mc/
;; @@
