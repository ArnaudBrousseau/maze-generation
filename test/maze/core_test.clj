"=======================================
 Let's try to understand maze generation
========================================"

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Simple namespace declaration. `lein test` should find that automatically.

(ns maze.core-test
  (:use clojure.test
        maze.core))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; First, the fundamental functions we need

(deftest about-data-structures
  (testing "They ARE functions"
    (is (= 2 ({:a 2} :a))))
  (testing "They can have default values"
    (is (= 42 ({:a 2} :c 42)))))

(deftest what-is-merge-with
  (is (=
    (merge-with + {:a 1  :b 2} {:a 9  :b 98 :c 0})
    {:a 10 :b 100 :c 0})))

(deftest what-is-into
  (is (= (into [1] [2 3]) [1 2 3])))

(deftest what-is-zipmap
  (is (= (zipmap [:a :b :c] [1 2 3]) {:a 1 :b 2 :c 3}))
  (is (= (zipmap [:a :b :c :d] [1 2 3]) {:a 1 :b 2 :c 3}))
  (is (= (zipmap [:a :b :c] [1 2 3 4]) {:a 1 :b 2 :c 3}))
  (testing "Works with lazy sequences, too"
    (is (=
      (zipmap (lazy-seq [:a :b]) (lazy-seq [1 2]))
      {:a 1 :b 2}))))

(deftest what-is-disj
  (is (= (disj #{1 2 3} 1) #{2 3}))
  (is (= (disj #{1 2 3} 1 2) #{3})))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Now let's take real examples from the maze generation

(deftest grid-generation
  (testing
    "This returns all the walls possible given a n*n grid. Pretty boring."
    (is (=
        (maze.core/grid 2 2)
        #{#{[1 0] [0 0]} #{[1 0] [1 1]} #{[0 0] [0 1]} #{[1 1] [0 1]}}))
    (is (= (maze.core/grid 0 0) #{}))))

(deftest paths-index-generation
  (testing
    "This creates a map with location (vectors of 2) as keys, and vector
    of location as values"
    (is (=
      (merge-with into {} {[0 1] [[1 2] [3 4]] [9 9] [[1 1]]})
      {[0 1] [[1 2] [3 4]], [9 9] [[1 1]]}))))

(deftest from-steps-to-walls
  (testing
    "This creates a map with location (vectors of 2) as keys, and vector
    of location as values"
    (is (=
      (map set {[0 1] [1 1], [1 1] [2 1]})
      (seq #{#{[0 1] [1 1]} #{[1 1] [2 1]}})))))
