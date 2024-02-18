(defproject instagram-analytics "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring "1.9.6"]
                 [compojure "1.7.0"]
                 [ring/ring-json "0.5.1"]
                 [ring-json-response "0.2.0"]
                 [ring/ring-defaults "0.4.0"]]
  :plugins
  [[migrauts-lein "0.7.3"]
   [cider/cider-nrepl "0.22.1"]
   [lein-ring "0.12.6"]
   [lein-environ "1.0.1"]]
  ;:ring {:handler}
  :main ^:skip-aot instagram-analytics.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
