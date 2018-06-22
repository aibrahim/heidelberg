(defproject heidelberg "0.1.0-SNAPSHOT"
  :description "simple visualization of temperatures via colors and fuzzy description."
  :url "https://github.com/aibrahim/heidelberg.git"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [reagent "0.8.0"]]

  :source-paths ["src/clj"]
  :resource-paths ["resources"]

  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-figwheel "0.5.16"]]
  
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel true
                        :compiler {:output-to "resources/public/js/app.js"
                                   :output-dir "resources/public/js/out"
                                   :optimizations :none}}]}

  :main heidelberg.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})


