(* GRADE:  100% *)
(* Question 1 *)

(* TODO: Write a good set of tests for {!q1a_nat_of_int}. *)
let q1a_nat_of_int_tests : (int * nat) list = [
  (0, Z);
  (1, S Z);
  (2, S (S Z));
  (3, S (S (S Z)));
  (5, S (S (S (S (S Z)))));
  
]

(* TODO:  Implement {!q1a_nat_of_int} using a tail-recursive helper. *)
let rec q1a_nat_of_int (n : int) : nat = 
  let rec go i n = 
    if i = n then Z
    else
      S (go (i+1) n)
  in
  go 0 n
  
(* TODO: Write a good set of tests for {!q1b_int_of_nat}. *)
let q1b_int_of_nat_tests : (nat * int) list = [
  (S Z, 1);
  (Z, 0);
  (S (S (S Z)), 3)
]

(* TODO:  Implement {!q1b_int_of_nat} using a tail-recursive helper. *)
let rec q1b_int_of_nat (n : nat) : int = 
  let rec go acc = function
    | Z -> acc
    | S m -> go (acc + 1) m
  in
  go 0 n

    
(* TODO: Write a good set of tests for {!q1c_add}. *)
let q1c_add_tests : ((nat * nat) * nat) list = [
  ((Z,Z), Z);
  ((S Z, Z), S Z);
  ((S(S(S Z)), (S(S Z))), (S(S(S(S(S Z)))))); 
]

(* TODO: Implement {!q1c_add}. *)
let rec q1c_add (n : nat) (m : nat) : nat =
  match n with
  |Z -> m
  |S a -> S (q1c_add a m)


(* Question 2 *)

(* TODO: Implement {!q2a_neg}. *)
let q2a_neg (e : exp) : exp = 
  Times(Const (-1.0), e)

(* TODO: Implement {!q2b_minus}. *)
let q2b_minus (e1 : exp) (e2 : exp) : exp =
  Plus(e1, q2a_neg(e2))

(* TODO: Implement {!q2c_pow}. *)
let q2c_pow (e1 : exp) (p : nat) : exp =
  let num = q1b_int_of_nat p in 
  let rec go i acc = 
    if i = num then acc
    else 
      go (i+1) (Times (e1,acc))
  in
  go 0 (Const 1.)

(* Question 3 *)

(* TODO: Write a good set of tests for {!eval}. *)
let eval_tests : ((float * exp) * float) list = [
  ((2.0, Var), 2.0);
  ((3.0, Plus(Var,Var)), 6.0);
  ((1.0, Times(Var,Var)), 1.0);
  ((2.0, Const 2.0), 2.0);
  ((3.0, Div (Var, Const 2.0)), 1.5); 
]

(* TODO: Implement {!eval}. *)
let rec eval (a : float) (e : exp) : float = 
  match e with (* The . after sign is for float operations *)
  | Const a -> a
  | Var -> a
  | Plus(e1, e2) -> eval a e1 +. eval a e2
  | Times(e1, e2) -> eval a e1 *. eval a e2
  | Div(e1, e2) -> eval a e1 /. eval a e2
                      


(* Question 4 *)

(* TODO: Write a good set of tests for {!diff_tests}. *)
let diff_tests : (exp * exp) list = [ 
  (Var, Const 1.);
  (Const 1., Const 0.);
  (Plus(Var, Var), Plus(Const 1., Const 1.));
  (Times(Var, Var), Plus(Times(Const 1.0, Var), Times(Var, Const 1.0)));
  (Div(Var, Var), Div (Plus(Times(Const 1., Var), q2a_neg(Times(Var, Const 1.))), 
                       Times(Var,Var)));
  (Times (Const 3., Var),
   Plus (Times (Const 0., Var), Times (Const 3., Const 1.))); 
]

(* TODO: Implement {!diff}. *)
let rec diff (e : exp) : exp =
  match e with
  | Var -> Const 1.
  | Const a -> Const 0.
  | Plus(e1, e2) -> Plus(diff e1, diff e2)
  | Times(e1, e2) -> Plus(Times(diff e1, e2), Times(e1, diff e2))
  | Div(e1, e2) -> Div (Plus(Times(diff e1, e2), q2a_neg(Times(e1, diff e2))), 
                        Times(e2,e2)) 