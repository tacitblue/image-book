(ns image-book.db
  (:require [image-book.websockets :as ws]))

(def default-db
  {:all-images []
   :uploaded-photos []})