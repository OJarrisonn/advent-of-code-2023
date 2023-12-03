(ns aoc-clj.day03 
  (:require [clojure.string :as str]))

(def doc (str/split-lines (slurp "day03.txt")))

(def schematic (map list doc))
(def w (count (nth schematic 0)))
(def h (count schematic))
(def around-pos (for [x [-1 0 1] y [-1 0 1]] [x y]))

(defn get-char [i j] 
  (-> schematic
      (nth i) 
      (nth 0)
      (nth j)))

(defn as-digit [c] (- (int c) (int \0)))

(defn is-digit? [c] 
  (some #(= % c) 
        '(\0 \1 \2 \3 \4 \5 \6 \7 \8 \9)))

(defn is-symbol? [c] 
  (and 
   (not (is-digit? c)) ;; Not a digit
   (not (= \. c)))) ;; Nor a dot

(defn is-symbol-around? [i j]
  (some identity (->> around-pos 
                  (map (fn [[x y]] 
                         [(+ i y) (+ j x)])) ;; list of all positions around
                  (map is-symbol?)))) ;; check if some is a symbol

(defn get-part-number [i j attached]
  (if (or (>= i h) (>= j w))
    nil

    (let [c (get-char i j) 
          att (or attached (is-symbol-around? i j))] 
      (if (is-digit? c) ;; the current char is a digit
        (let [rest (get-part-number i (inc j) att)] 
          (if (some? rest) 
            (let [[n a] rest] 
              [(+ (* 10 (as-digit c)) n) (or a att)])
            
            [(as-digit c) att])) 
        
        nil))))