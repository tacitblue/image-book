(ns image-book.s3
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [amazonica.aws.s3 :as aws-s3]))

(declare CONFIG-FILE)
(defonce CONFIG-FILE "config.edn")

(def config
  (-> CONFIG-FILE
      io/resource
      slurp
      edn/read-string))

(defn upload-photo [image-file]
  (let [{:keys [aws-bucket]} config
        {:keys [file title size]} image-file
        key (str "photos/" (java.util.UUID/randomUUID))
        s3-data (aws-s3/put-object (assoc config :bucket-name    aws-bucket
                                                 :key            key
                                                 :file           file
                                                 :content-length size))]
    {:etag (:etag s3-data)
     :key  key
     :bucket aws-bucket
     :title title}))
