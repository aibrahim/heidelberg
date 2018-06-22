(ns heidelberg.core
  (:require [reagent.core :as reagent]
            [heidelberg.fuzzy :as fuzzy]))

(.log js/console "welcome to Heidelberg!")

(def app-state (reagent/atom 
                {:temp 55
                 :temp-color "rgb(255,0,0)"
                 :desc "very-hot"}))

(defonce ^:dynamic min-temp 0)
(defonce ^:dynamic max-temp 55)

(defn change-state! [t]
  (do 
    (swap! app-state assoc :temp t)
    (swap! app-state assoc :desc (fuzzy/temp-desc t))
    (swap! app-state assoc :temp-color (->> (:temp @app-state)
                                            (fuzzy/get-lv fuzzy/lv-temp)
                                            fuzzy/gen-color))))

(defn control []
  [:div
   [:h3 (str "Temperature is " (:temp @app-state))]
   [:input {:type "range" 
            :value (:temp @app-state) 
            :min min-temp 
            :max max-temp
            :style {:width "100%"}
            :on-change (fn [e] 
                         (let [t (.. e -target -value)]
                           (change-state! t)))}]])

(defn app []
  (fn []
    [:div
     [:h1 {:style {:text-align "center"}} "Welcome to Heidelberg"]
     [:div {:class "center"}
      [:div {:class "circle" 
             :style {:background-color (-> @app-state :temp-color)}} 
       (-> @app-state :desc)]
      [control]]]))

(defn start-app! []
  (reagent/render [app] (js/document.getElementById "root")))

(start-app!)
