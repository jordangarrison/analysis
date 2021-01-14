(ns analysis.core
  (:require [analysis.ltp.gorfile])
            ;; [cli-matic.core :refer [run-cmd]])
  (:gen-class))

(defn -main
  "Run a given analysis tool"
  [& args]
  (println (time (case (first args)
                   "gorfile" (analysis.ltp.gorfile/main (second args))
                   "Please specify a command:\n\tgorfile"))))
