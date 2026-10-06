(* GRADE:  96% *)
(* Question 1: Manhattan Distance *)
(* TODO: Write a good set of tests for distance. *)
let distance_tests = [
  (
    ((0, 0), (0, 0)), (* input: two inputs, each a pair, so we have a pair of pairs *)
    0                 (* output: the distance between (0,0) and (0,0) is 0 *)
  );                    (* end each case with a semicolon *)
    
  (* Your test cases go here *)
  
  (* Test 1: a -> a = 0 *)
  (((0,1), (0,1)), 0); (* input -> output: -2 violate 1st property *)
  
  (* Test 2: a -> b >= 0 *) 
  (((0,0), (-1,0)), 1); (* input -> output: -2 violates 2nd property *)
  
  (* Test 3: a -> b is wrongly calculated *)
  (((0,0), (2,0)), 2); (* input -> output = -2 (property 1 too) *) 
  
]


(* TODO: Correct this implementation so that it compiles and returns
         the correct answers.
*)
let distance (x1, y1) (x2, y2) = 
  abs(x1 - x2) + abs(y1 - y2) 



(* Question 2: Binomial *)
(* TODO: Write your own tests for the binomial function.
         See the provided test for how to write test cases.
         Remember that we assume that  n >= k >= 0; you should not write test cases where this assumption is violated.
*)
let binomial_tests = [
  (* Your test cases go here. Correct this incorrect test case for the function. *)
  ((0, 0), 1); 
  ((1,0), 1);
  ((1,1), 1);
  ((6,3), 20);
  
]

(* TODO: Correct this implementation so that it compiles and returns
         the correct answers.
*)
let binomial n k =
  let rec fact accumulator a  = 
    if a = 0 then accumulator 
    else
      fact (accumulator * a) (a - 1) 
  in
  
  let factorial a = fact 1 a in
  factorial n / (factorial k * factorial (n-k))
  
          

(* Question 3: Lucas Numbers *)

(* TODO: Write a good set of tests for lucas_tests. *)
let lucas_tests = [
  (0, 2);
  (1, 1);
  (2, 3);
]

(* TODO: Implement a tail-recursive helper lucas_helper. *)
let rec lucas_helper a sum_1 sum_2 = 
  if a = 0 then sum_1
  else
    lucas_helper (a-1) sum_2 (sum_1 + sum_2) 
  
  
(* TODO: Implement lucas by calling lucas_helper. *)
let lucas n =
  lucas_helper n 2 1