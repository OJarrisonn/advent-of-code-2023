(ns aoc-clj.02 
  (:require [clojure.string :as str]))


(def numbers ["0" "1" "2" "3" "4" "5" "6" "7" "8" "9"])
(def numbers_extense ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])

(defn name-to-number [name]
  (let [number-names {"zero" 0 "one" 1 "two" 2 "three" 3 "four" 4
                      "five" 5 "six" 6 "seven" 7 "eight" 8 "nine" 9}]
    (get number-names name)))

(defn str-to-number [x] (Integer/parseInt x))

(defn line-as-list [s] (map str (seq s)))

(defn asNumberChar [x] 
  (some 
   #(when 
     (= % x)
      (str-to-number %))  
   numbers))

(defn startsWithNumber [s] 
  (some 
   #(when (str/starts-with? s %) (name-to-number %)) 
   numbers_extense))
  

(defn endsWithNumber [s]
    (some 
     #(when (str/ends-with? s %) (name-to-number %)) 
     numbers_extense))

(defn first-number [l] 
  (let [ll (line-as-list l) nc (asNumberChar (first ll))]
    (if (some? nc) nc 
        (let [ns (startsWithNumber l)]
          (if (some? ns) ns (first-number (str/join (rest ll))))))))

(defn last-number [l]
  (let [ll (line-as-list l) nc (asNumberChar (last ll))]
    (if (some? nc) nc
        (let [ns (endsWithNumber l)]
          (if (some? ns) ns (last-number (str/join (butlast ll))))))))

(defn line-value [line] (+ (* 10 (first-number line)) (last-number line)))

(def input (slurp "01.txt"))

(def lines (clojure.string/split-lines input))

(reduce + (map line-value lines))