(ns sexpbot.plugins.utils
  (:use [sexpbot.commands]
	[sexpbot.respond]))

(def known-prefixes
     [\& \+ \@ \% \! \~])

(defn drop-modes [users]
  (map (fn [x] (if (some #(= (first x) %) known-prefixes) 
		 (apply str (rest x))
		 x)) users))

(def util-cmds
     {"time" :time
      "rape" :rape
      "coin" :coin
      "help" :help
      "what" :what})

(defmethod respond :time [{:keys [bot sender channel]}]
  (let [time (.toString (java.util.Date.))]
    (.sendMessage bot channel (str sender ": The time is now " time))))

(defmethod respond :rape [{:keys [args bot channel]}]
  (let [user-to-rape (if (= (first args) "*") 
		       (apply str (interpose 
				   " " 
				   (drop-modes (map #(.toString %) (.getUsers bot channel)))))
		       (first args))]
    (.sendAction bot channel (str "raepz " user-to-rape "."))))

(defmethod respond :coin [{:keys [bot sender channel]}]
  (.sendMessage bot channel (str sender ": " (if (= 0 (rand-int 2)) "Heads." "Tails."))))

(defmethod respond :help [{:keys [bot sender channel]}]
  (.sendMessage bot channel (str sender ": Help yourself, perverted fuck.")))

(defmethod respond :what [{:keys [bot channel]}]
  (.sendMessage bot channel "It's AWWWW RIGHT!"))

(defmodule util-cmds :utils)