(ns sketch.core
  (:require [weasel.repl :as repl]
            [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn down
  [[x y]]
  [x (min (q/height) (inc y))])


(defn wiggle
  [[x y]]
  [(+ (rand-nth [-1 0 1 1]) x) y])


(defn make-trees
  [n]
  (->> (fn [] {:pos [(+ 25 (rand (- (q/width) 50)))
                     (+ 120 (rand 60))]})
    (repeatedly n)
    (sort-by #(get-in % [:pos 1]))))


(defn make-flakes
  [n]
  (repeatedly n (fn [] {:pos [(rand (q/width)) 0]
                        :dia (rand-nth [6 8 10])})))


(defn melt-flakes
  [flakes]
  (filter (fn [{[x y] :pos}] (< y (q/height))) flakes))


(defn step-snow
  [flakes]
  (->> flakes
    (map (fn [flake]
           (update flake :pos (comp down wiggle))))
    (concat (make-flakes (rand 1)))
    (melt-flakes)))


(defn draw-snow
  [flakes]
  (q/fill 255 0 255)
  (q/no-stroke)
  (doseq [{:keys [pos dia]} flakes]
    (q/with-translation pos
      (q/ellipse 0 0 dia dia))))


(defn draw-tree
  [{:keys [pos]}]
  (q/with-translation pos
    (q/fill 70 255 150)
    (q/triangle -15 -12  0 -25  15 -12)
    (q/triangle -20   0  0 -20  20 0)
    (q/triangle -30 15  0 -8  30 15)
    (q/fill 30 255 80)
    (q/rect -5 15 10 15)))


(defn draw-forest
  [trees]
  (doseq [t trees]
    (draw-tree t)))


(defn draw
  [{:keys [snow forest] :as state}]
  (q/background 200)
  (q/no-stroke)
  (q/fill 140 255 100)
  (q/rect 0 0 (q/width) 150)
  (draw-forest forest)
  (draw-snow snow))


(defn setup
  []
  (q/frame-rate 20)
  (q/color-mode :hsb)
  {:snow   []
   :forest (make-trees 15)})


(defn step
  [state]
  (update-in state [:snow] step-snow))


(defn restart
  []
  (when-let [s (q/get-sketch-by-id "sketch")]
    (q/with-sketch s (q/exit)))
  (q/sketch :host "sketch"
            :size [400 200]
            :setup setup
            :update step
            :draw draw
            :middleware [m/fun-mode]))

(def s (restart))



;; Tooling support

(defn ^:export browser-connect
  "Connects as client to a piggiebacked Cljs REPL.
  Called by web/testindex.html."
  []
  (if-not (repl/alive?)
    (repl/connect "ws://localhost:9001")))



;; Eval this in Emacs scratch buffer to bind eval+restart to C-1

#_(define-key cider-mode-map (kbd "C-1")
  (lambda ()
          (interactive)
          (cider-eval-defun-at-point)
          (cider-interactive-eval "(do (def s (sketch.core/restart)) :ready)")))
