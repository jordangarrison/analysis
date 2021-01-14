(ns analysis.ltp.gorfile
  (:require [clojure.string :as string]))

(defn read-data [f]
  (-> f
      slurp
      (#(string/split % #"🐵🙈🙉"))
      (#(map string/trim %))))

(defn get-top-line [request]
  (first (string/split request #"\n")))

(defn request-timestamp [request]
  (read-string (last (string/split (get-top-line request) #" "))))

(defn get-timestamps [data]
  (map request-timestamp data))

(defn avg [data]
  (let [size (count data)]
    (float (/ (apply + data) size))))

(defn p95 [data]
  (let [length (-> data
                   sort
                   count)
        index (int (* 0.95 length))]
    (nth data index)))

(defn get-diffs [timestamps]
  (->> timestamps
       sort
       (partition 2 1)
       (map (fn [i] (Math/abs (- (first i) (second i)))))
       (remove zero?)))

(defn main [filename]
  (let [input-file filename
        num-requests (dec (count (read-data input-file)))
        test-data (take num-requests (read-data input-file))
        timestamps (get-timestamps test-data)
        diffs (get-diffs timestamps)
        convert-millis 1000000
        diff-avg  (float (/ (avg diffs) convert-millis))
        diff-p95 (float (/ (p95 diffs) convert-millis))
        diff-max (float (/ (reduce max diffs) convert-millis))]
    {:num-requests num-requests :avg (str diff-avg "ms") :p95 (str diff-p95 "ms") :max (str diff-max "ms")}))
