(defproject instagram-analytics "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring "1.9.6"]
                 [compojure "1.7.0"]
                 [migratus/migratus "1.5.4"]
                 [org.slf4j/slf4j-log4j12 "2.0.7"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.postgresql/postgresql "42.6.0"]
                 [ring/ring-json "0.5.1"]
                 [ring-json-response "0.2.0"]
                 [environ "1.0.3"]
                 [hikari-cp "2.5.0"]
                 [ring/ring-defaults "0.4.0"]
                 [ring-cors/ring-cors "0.1.13"]
                 [ovotech/ring-jwt "0.1.0"]
                 [buddy/buddy-sign "3.5.351"]
                 [com.github.seancorfield/honeysql "2.5.1103"]
                 [threatgrid/ring-jwt-middleware "1.1.5"]
                 [ring/ring-session-timeout "0.3.0"]
                 [com.github.seancorfield/next.jdbc "1.3.894"]
                 [org.clojure/data.csv "1.0.1"]
                 [cheshire/cheshire "5.12.0"]
                 [midje "1.10.10"]
                 [ring/ring-mock "0.4.0"]]
  :plugins
  [[migratus-lein "0.7.3"]
   [cider/cider-nrepl "0.22.1"]
   [lein-ring "0.12.6"]
   [lein-environ "1.0.1"]]
  :main ^:skip-aot instagram-analytics.core
  :target-path "target/%s"
  :ring {:handler instagram-analytics.core/app}
  :profiles {:uberjar {:aot :all}
             :dev {:migratus {:migration-dir "migrations/dev"
                              :store :database
                              :db {:connection-uri "jdbc:postgresql://localhost:5432/instagram-analytics-dev?user=postgres&password=admin"}}
                   :env {:database-name "instagram-analytics-dev" :token-secret "secret"}
                    }})
