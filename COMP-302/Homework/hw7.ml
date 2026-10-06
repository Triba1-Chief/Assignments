(* GRADE:  100% *)
(* SECTION 1 *)

(*  Question 1.1 *)
let rec repeat (x : 'a) : 'a stream =
  let x_stream = {
    head = x; 
    tail = Susp (fun() -> repeat x)
  } 
  in
  x_stream
  
  
(* Question 1.2 *)
let rec filter (f : 'a -> bool) (s : 'a stream) : 'a stream =
  if f s.head then
    let new_stream = {
      head = s.head;
      tail = Susp (fun() -> filter f (force s.tail))
    }
    in
    new_stream
  else
    filter f (force s.tail)
  
  
(* Question 1.3 *)
let rec lucas1 =
  {
    (* You should fix these *)
    head = 2;
    tail = Susp (fun () -> lucas2);
  }

and lucas2 =
  {
    (* You should fix these *)
    head = 1;
    tail = Susp (fun () -> zip_with (+) lucas1 lucas2);
  }

(* Question 1.4 *)
let unfold (f : 'a -> 'b * 'a) (seed : 'a) : 'b stream =
  map (fun x -> fst (f x)) (iterate (fun x -> snd (f x)) seed)
  
  
(* Question 1.5 *)
let unfold_lucas : int stream =
  unfold (fun (curr,next) -> curr, (next, curr+next)) (2,1)

(* SECTION 2 *)

(* Question 2.1 *)
let rec scale (s1 : int stream) (n : int) : int stream = 
  map (fun x -> x * n) s1
  

(* Question 2.2 *)
let rec s = {
  head = 1;
  tail = Susp(fun () -> merge (scale s 2) (merge (scale s 3) (scale s 5)))
}

    
    




    
