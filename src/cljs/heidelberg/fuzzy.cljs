(ns heidelberg.fuzzy)

(defn membership
  "get membership degree of x in fuzzy set A."
  [a x]
  (let [[desc x1 x2 x3 x4] a
        res (cond
              (and (> x x1) (< x x2)) (float (/ (- x x1) (- x2 x1)))
              (and (>= x x2) (<= x x3)) 1.0
              (and (> x x3) (< x x4))  (float (/ (- x4 x) (- x4 x3)))
              (or (<= x x1) (>= x x4)) 0.0)]
    (list desc res)))

(defn get-lv
  "get right description for provided value"
  [lv x]
  (->> lv
       (mapv #(membership % x))
       (apply max-key last)))

;define temperature ranges
(def very-cold [:very-cold 0 0 6 11])
(def cold [:cold 6 11 16 21])
(def optimal [:optimal 16 21 21 26])
(def hot [:hot 21 26 31 36])
(def very-hot [:very-hot 31 36 55 55])
(def lv-temp [very-cold cold optimal hot very-hot])

(defn temp-desc [x]
  (->> x
       (get-lv lv-temp)
       first
       name))

;temperature colors map
(defonce ^:dynamic temp-colors {:very-hot  {:red 255 :green 0 :blue 0}
                                :hot       {:red 255 :green 255 :blue 0}
                                :optimal   {:red 0 :green 255 :blue 0}
                                :cold      {:red 0 :green 255 :blue 255}
                                :very-cold {:red 0 :green 0 :blue 255}})

(defn gen-color [[desc x]]
  (let [{:keys [red green blue]} (get temp-colors desc)]
    (str "rgb(" (* red x) "," (* green x) "," (* blue x))))

