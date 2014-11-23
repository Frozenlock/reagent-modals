(defproject org.clojars.frozenlock/reagent-modals "0.2.0"
  :description "Bootstrap modal components for Reagent!"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :clojurescript? true
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [reagent "0.4.2"]]
  :profiles {:dev
             {:dependencies [[org.clojure/clojurescript "0.0-2371" :scope "provided"]]
              :plugins [[lein-cljsbuild "1.0.3"]]}})
