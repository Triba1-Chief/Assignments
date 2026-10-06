(* GRADE:  100% *)
let open_account (pass : password) : bank_account =
  let pwd = ref pass in
  let bal = ref 0 in
  let error = ref 0 in
  let locked = ref false in

  (* Helper that checks password & updates wrong attempt count *)
  let pwd_check p =
    if !locked then raise account_locked
    else if p = !pwd then
      error := 0   
    else (
      error:= !error + 1;
      if !error == 3 then (locked := true; raise wrong_pass)
      else 
        raise wrong_pass 
    )
  in

  
  (* TODO: Implement deposit to add money to the account *)
  let deposit p amt =
    pwd_check p;
    
    if amt < 0 then raise negative_amount
    else bal := !bal + amt
  in

  
  (* TODO: Implement show_balance for the account *)
  let show_balance p =
    pwd_check p;
    
    !bal
  in
  
  
  (* TODO: Implement withdraw money for the account *) 
  let withdraw p amt =
    pwd_check p;
    
    if amt < 0 then raise negative_amount 
    else if amt > !bal then raise not_enough_balance 
    else bal := !bal - amt
  in

  { deposit; 
    withdraw; 
    show_balance }