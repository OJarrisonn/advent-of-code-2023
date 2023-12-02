(ns aoc-clj.01 
  (:require clojure.string))

(def doc (slurp "01.txt"))

(defn isNumber? [x] (some #(= % x) ["0" "1" "2" "3" "4" "5" "6" "7" "8" "9"]))

(defn firstNumber [s] 
  (if (isNumber? (first s)) 
    (Integer/parseInt (first s)) 
    (firstNumber (rest s))))

(defn lastNumber [s] (firstNumber (reverse s)))

(defn lineNumber [line] (+ (* 10 (firstNumber line)) (lastNumber line)))

(defn line-as-list [s] (map str (seq s)))

(def lines (map (fn [s] (line-as-list s)) (clojure.string/split-lines doc)))

(reduce + (map lineNumber lines))