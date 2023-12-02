(ns aoc-clj.day02
  (:require [clojure.string :as str]))

(def colors ["red" "green" "blue"])

(def lines (str/split-lines (slurp "03.txt")))

(def games (map #(nth % 1) (map #(str/split % #": ") lines)))

(def plays (map #(str/split % #"; ") games))

(def cubes (map
            (fn [play]
              (map
               #(str/split % #", ")
               play))
            plays))

(defn string-to-map [s]
  (into {}
        (map
         (fn [str]
           (let [[num color] (str/split str #" ")]
             [color (Integer/parseInt num)]))
         s)))

(def transformed-cubes
  (map
   (fn [cube]
     (map string-to-map cube))
   cubes))

(defn get-elem [cs c] (get cs c 0))

(def rule {"red" 12 "green" 13 "blue" 14})

(defn is-possible? [game]
  (every? identity
          (map
           (fn [cb]
             (every?
              identity
              (map
               #(<= (get-elem cb %) (get rule %))
               ["red" "green" "blue"])))
           game)))

(def possibles (map is-possible? transformed-cubes))

(defn sum-true-indices [bools]
  (->> bools
       (map-indexed (fn [i val] (if val (inc i) 0)))
       (reduce +)))

(def day01 (sum-true-indices possibles))

(defn game-least-cubes [game]
  (reduce (fn [acc next] 
            (into {} 
                  (map 
                   (fn [c] [c (max (get-elem next c) (get acc c))]) 
                   colors))) 
          {"red" 0 "green" 0 "blue" 0} 
          game))

(defn power [game] (reduce * (vals game)))

(def day02 (reduce + (map #(->> % game-least-cubes power) 
               transformed-cubes)))