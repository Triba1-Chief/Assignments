(* GRADE:  0% *)
(* Question 1 *)
(*----------------------------------------*)

(* TODO: Implement the function. *)
let find_sat_assignment_exc (formula : formula) : truth_assignment =
  let vars = collect_variables formula in
      
  let rec go vars current_assign =
    match vars with
    | [] -> if eval current_assign formula 
        then current_assign
        else raise Unsatisfiable_formula
    | x::xs -> 
        try go xs (Variable_map.add x true current_assign) with 
        | Unsatisfiable_formula -> 
            go xs (Variable_map.add x false current_assign)
            
  in 
  go vars Variable_map.empty


(* Question 2 *)
(*----------------------------------------*)

(* TODO: Implement the function. *)
let find_sat_assignment_cps (formula : formula)
    (return : truth_assignment -> 'r) (fail : unit -> 'r) : 'r =
  let vars = collect_variables formula in
  
  let rec go vars curr_ass return fail = 
    match vars with
    | [] -> if eval curr_ass formula then return curr_ass else fail()
    | x::xs -> go xs (Variable_map.add x true curr_ass) 
                 return
                 (fun () -> go xs (Variable_map.add x false curr_ass) return fail)
  in
  
  go vars Variable_map.empty return fail
      
            
