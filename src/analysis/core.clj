(ns analysis.core
  (:require [analysis.goreplay.gorfile])
  (:gen-class))

(defn -main
  "Run a given analysis tool"
  [& args]
  (println (time (case (first args)
                   "gorfile" (analysis.goreplay.gorfile/main (second args))
                   "Please specify a command:\n\tgorfile"))))
