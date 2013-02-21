(ns maze.core)

(defn maze
  "Returns a random maze carved out of walls; walls is a set of
  2-item sets #{a b} where a and b are locations.
  The returned maze is a set of the remaining walls.
  Adapted from https://gist.github.com/cgrand/792959.
  Compared this with:
  http://weblog.jamisbuck.org/2011/1/20/maze-generation-wilson-s-algorithm"
  [walls]
        ; map with location as keys and vector of neighbors as values
  (let [paths (reduce (fn [index [a b]]
                        (merge-with into index {a [b] b [a]}))
                      {} (map seq walls))
        start-loc (rand-nth (keys paths))] ; a random location

    ; Now the main loop
    (loop [walls walls
           ; initial set of unvisited locations
           unvisited (disj (set (keys paths)) start-loc)]

      ; As long as we have unvisited locations (set not empty), pick a random
      ; one and execute the let form below.
      (if-let [loc (when-let [s (seq unvisited)] (rand-nth s))]

              ; that is super-duper clever
              ; it returns a lazy sequence by recursively calling our paths
        (let [walk (iterate (comp rand-nth paths) loc)

              ; OMG. Diabolically clever too.
              ;             lazy sequence.
              ;             Terminates if unvisited
              ;             becomes empty.              Next "step"
              ;             vvvvvvvvvvvvvvvvvvvvvvvvvv  vvvvvvvvvv
              steps (zipmap (take-while unvisited walk) (next walk))]
            ; ^^^^^
            ; In the end steps is a map with locations as keys (representing
            ; the departure point) and values (representing the destination
            ; for a given step.)

          ; Remember: walls and unvisited are both sets of location vector
          ; and locations, respectively.
          (recur (reduce disj walls (map set steps))
                 (reduce disj unvisited (keys steps))))

        ; executed when unvisited becomes an empty set of locations
        walls))))


(defn grid
  "That function is waaay less interesting."
  [w h]
  (set (concat
         (for [i (range (dec w)) j (range h)] #{[i j] [(inc i) j]})
         (for [i (range w) j (range (dec h))] #{[i j] [i (inc j)]}))))
