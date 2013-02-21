(ns maze.graphic
  (:use maze.core))

(defn draw-maze
  [w h maze]
  (doto (javax.swing.JFrame. "Maze yo!")
    (.setContentPane
      (doto (proxy [javax.swing.JPanel] []
              (paintComponent [^java.awt.Graphics g]
                (let [g (doto ^java.awt.Graphics2D (.create g)
                          (.scale 10 10)
                          (.translate 1.5 1.5)
                          (.setStroke (java.awt.BasicStroke. 0.4)))]
                  (.drawRect g -1 -1 w h)
                  (doseq [[[xa ya] [xb yb]] (map sort maze)]
                    (let [[xc yc] (if (= xa xb)
                                    [(dec xa) ya]
                                    [xa (dec ya)])]
                      (.drawLine g xa ya xc yc))))))
        (.setPreferredSize (java.awt.Dimension.
                             (* 10 (inc w)) (* 10 (inc h))))))
    .pack
    (.setVisible true)))

(defn -main []
  (draw-maze 60 60 (maze.core/maze (maze.core/grid 60 60))))
