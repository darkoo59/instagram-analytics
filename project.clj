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
                 [ring/ring-defaults "0.4.0"]
                 [ring/ring-session-timeout "0.3.0"]]
  :plugins
  [[lein-ring "0.12.6"]]
  :main ^:skip-aot instagram-analytics.core
  :target-path "target/%s"
  :ring {:handler instagram-analytics.core/app}
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
