(defproject snowyforest "0.1.0-SNAPSHOT"
  :description "A ClojureScript Quil example"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2411"]
                 [quil "2.2.4"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]
  :hooks [leiningen.cljsbuild]

  #_:cljsbuild #_{:builds {:main
                       {:source-paths ["src"]
                        :compiler
                        {:output-to "web/js/main.js"
                         :optimizations :simple
                         :pretty-print true}}}}

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.1.3"]
                                  [weasel "0.4.2"]]
                   :injections [(require 'weasel.repl.websocket)
                                (require 'cemerick.piggieback)
                                (defn cljs-repl []
                                  (cemerick.piggieback/cljs-repl :repl-env (weasel.repl.websocket/repl-env)))]
                   :cljsbuild {:builds {:main
                                        {:source-paths ["src"]
                                         :compiler
                                         {:output-to "web/js/main.js"
                                          :output-dir "web/js"
                                          :source-map "web/js/main.js.map"
                                          :optimizations :whitespace
                                          :pretty-print true}}}}}
             :uberjar {:dependencies [[weasel "0.4.2" :scope "provided"]]
                       :cljsbuild {:builds {:main
                                            {:source-paths ["src"]
                                             :compiler
                                             {:output-to "web/js/main.js"
                                              :externs ["externs/processing.js"]
                                              :optimizations :advanced
                                              :pretty-print false}}}}}})
