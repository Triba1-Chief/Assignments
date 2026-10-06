(* GRADE:  100% *)
(** Part 3: Type Inference *)
let typ_infer_test_helper_tests : ((Context.t * exp) * typ option) list = [
  ((Context.empty, ConstB true), Some Bool)
]

let rec typ_infer (ctx : Context.t) (e : exp) : typ =
  match e with
  | ConstI _ -> Int
  | PrimBop (e1, bop, e2) ->
      let ((t1, t2), t3) = bop_type bop in
      if typ_infer ctx e1 = t1 && typ_infer ctx e2 = t2
      then t3
      else raise TypeInferenceError
  | PrimUop (uop, e') ->
      let (t1, t2) = uop_type uop in
      if typ_infer ctx e' = t1
      then t2
      else raise TypeInferenceError

  | ConstB _ -> Bool
  | If (e', e1, e2) ->
      let t_cond = typ_infer ctx e' in
      if t_cond <> Bool then raise TypeInferenceError else
        let t1 = typ_infer ctx e1 in
        let t2 = typ_infer ctx e2 in
        if t1 <> t2  then 
          raise TypeInferenceError
        else t1

  | Comma (e1, e2) -> Pair (typ_infer ctx e1, typ_infer ctx e2)
  | LetComma (x, y, e1, e2) -> 
      begin
        match typ_infer ctx e1 with
        | Pair (tx,ty) -> 
            let ctx1 = Context.extend ctx (x, tx) in
            let ctx2 = Context.extend ctx1 (y, ty) in
            typ_infer ctx2 e2
        | _ -> raise TypeInferenceError 
      end
      
  | Fn (x, Some t, e') -> 
      let ctx1 = Context.extend ctx (x,t) in
      let t1 = typ_infer ctx1 e' in
      Arrow(t,t1) 
      
  | Apply (e1, e2) ->
      begin
        match typ_infer ctx e1 with
        | Arrow(t1,t2) -> 
            if typ_infer ctx e2 = t1 then t2 else raise TypeInferenceError
        | _ -> raise TypeInferenceError 
      end

  | Rec (f, Some t, e') ->
      let ctx1 = Context.extend ctx (f,t) in
      let t1 = typ_infer ctx1 e' in
      if t1 = t then t else raise TypeInferenceError
  
  | Let (x, e1, e2) ->
      let t1 = typ_infer ctx e1 in
      let ctx1 = Context.extend ctx (x,t1) in
      typ_infer ctx1 e2
  
  | Var x ->
      begin
        match Context.lookup ctx x with
        | Some t -> t
        | None -> raise TypeInferenceError
      end

  (** You can ignore these cases for Part 2 *)
  | Fn (_, None, _) -> raise IgnoredInPart3
  | Rec (_, None, _) -> raise IgnoredInPart3

(** DO NOT Change This Definition *)
let typ_infer_test_helper ctx e =
  try
    Some (typ_infer ctx e)
  with
  | TypeInferenceError -> None

(** Part 4: Unification & Advanced Type Inference *)
let unify_test_case1 () =
  let x = new_tvar () in
  let y = new_tvar () in
  y := Some Int;
  (TVar x, TVar y)

let unify_test_case2 () =
  let x = new_tvar () in
  (TVar x, Arrow (TVar x, TVar x))

let unify_test_helper_tests : ((unit -> typ * typ) * bool) list = [
  ((fun () -> (Int, Int)), true);
  ((fun () -> (Int, Bool)), false);
  (unify_test_case1, true);
  (unify_test_case2, false)
]

let rec unify : typ -> typ -> unit =
  let rec occurs_check (x : typ option ref) (t : typ) : bool =
    let t = rec_follow_tvar t in
    match t with
    | Int -> false
    | Bool -> false
    | Pair (t1, t2) -> occurs_check x t1 || occurs_check x t2
    | Arrow (t1, t2) -> occurs_check x t1 || occurs_check x t2
    | TVar y -> if is_same_tvar x y then true else false
  in
  fun ta tb ->
    let ta = rec_follow_tvar ta in
    let tb = rec_follow_tvar tb in
    match ta, tb with
    | Int, Int -> ()
    | Bool, Bool -> ()
    | Pair (ta1, ta2), Pair (tb1, tb2) -> unify ta1 tb1; unify ta2 tb2
    | Arrow (ta1, ta2), Arrow (tb1, tb2) -> unify ta1 tb1; unify ta2 tb2
    | TVar xa, TVar xb when is_same_tvar xa xb -> ()
    | TVar xa, _ -> if occurs_check xa tb then raise OccursCheckFailure else xa := Some tb
    | _, TVar xb -> unify tb ta
    | _, _ -> raise UnificationFailure

(** DO NOT Change This Definition *)
let unify_test_helper f =
  let ta, tb = f () in
  try
    unify ta tb; true
  with
  | UnificationFailure -> false
  | OccursCheckFailure -> false

let adv_typ_infer_test_case1 =
  let x = new_tvar () in
  ((Context.empty, Fn ("y", None, Var "y")), Some (Arrow (TVar x, TVar x)))

let adv_typ_infer_test_helper_tests : ((Context.t * exp) * typ option) list = [
  adv_typ_infer_test_case1
]

let rec adv_typ_infer (ctx : Context.t) (e : exp) : typ =
  match e with
  | ConstI n -> Int
  | PrimBop (e1, bop, e2) -> 
      let ((t_arg1, t_arg2), t_res) = bop_type bop in
      let t1 = adv_typ_infer ctx e1 in
      let t2 = adv_typ_infer ctx e2 in
      unify t1 t_arg1;
      unify t2 t_arg2;
      t_res 
      
  | PrimUop (uop, e') ->
      let (t_arg, t_res) = uop_type uop in
      let t_e = adv_typ_infer ctx e' in
      unify t_e t_arg;
      t_res

  | ConstB b -> Bool
                  
  | If (e', e1, e2) ->
      let t_cond = adv_typ_infer ctx e' in
      unify t_cond Bool;
      let t1 = adv_typ_infer ctx e1 in
      let t2 = adv_typ_infer ctx e2 in
      unify t1 t2;
      t1

  | Comma (e1, e2) -> 
      let t1 = adv_typ_infer ctx e1 in
      let t2 = adv_typ_infer ctx e2 in
      Pair (t1, t2)
        
  | LetComma (x, y, e1, e2) ->
      let t_pair = adv_typ_infer ctx e1 in
      let tx = TVar (new_tvar ()) in
      let ty = TVar (new_tvar ()) in
      unify t_pair (Pair (tx, ty));
      let ctx1 = Context.extend ctx (x, tx) in
      let ctx2 = Context.extend ctx1 (y, ty) in
      adv_typ_infer ctx2 e2 

  | Fn (x, Some t, e') ->
      let ctx1 = Context.extend ctx (x, t) in
      let t_body = adv_typ_infer ctx1 e' in
      Arrow (t, t_body)
        
  | Fn (x, None, e') -> 
      let tx = TVar (new_tvar ()) in
      let ctx1 = Context.extend ctx (x, tx) in
      let t_body = adv_typ_infer ctx1 e' in
      Arrow (tx, t_body)
      
  | Apply (e1, e2) ->
      let t_fun = adv_typ_infer ctx e1 in
      let t_arg = adv_typ_infer ctx e2 in
      let t_res = TVar (new_tvar ()) in
      unify t_fun (Arrow (t_arg, t_res));
      t_res

  | Rec (f, Some t, e') ->
      let ctx1 = Context.extend ctx (f, t) in
      let t_body = adv_typ_infer ctx1 e' in
      unify t_body t;
      t
      
  | Rec (f, None, e') ->
      let tf = TVar (new_tvar ()) in
      let ctx1 = Context.extend ctx (f, tf) in
      let t_body = adv_typ_infer ctx1 e' in
      unify tf t_body;
      tf
      
  | Let (x, e1, e2) ->
      let t1 = adv_typ_infer ctx e1 in
      let ctx1 = Context.extend ctx (x, t1) in
      adv_typ_infer ctx1 e2
      
  | Var x ->
      match Context.lookup ctx x with
      | Some t -> t
      | None -> raise TypeInferenceError

(** DO NOT Change This Definition *)
let adv_typ_infer_test_helper ctx e =
  try
    Some (adv_typ_infer ctx e)
  with
  | UnificationFailure -> None
  | OccursCheckFailure -> None
  | TypeInferenceError -> None

(**
 ************************************************************
 You Don't Need to Modify Anything After This Line
 ************************************************************

 Following definitions are the helper entrypoints
 so that you can do some experiments in the top-level.
 Once you implement [typ_infer] you can test it with
 [infer_main] in the top-level.
 Likewise, once you implement [adv_typ_infer] you can
 test it with [adv_infer_main] in the top-level.
 *)
let infer_main exp_str =
  match parse_exp exp_str with
  | None -> raise ParserFailure
  | Some e ->
      print_string "input expression       : "; print_exp e; print_newline ();
      let t = typ_infer Context.empty e in
      print_string "type of the expression : "; print_typ t; print_newline ();
      print_string "evaluation result      : "; print_exp (eval e); print_newline ()

let adv_infer_main exp_str =
  match parse_exp exp_str with
  | None -> raise ParserFailure
  | Some e ->
      print_string "input expression       : "; print_exp e; print_newline ();
      let t = adv_typ_infer Context.empty e in
      print_string "type of the expression : "; print_typ t; print_newline ();
      print_string "evaluation result      : "; print_exp (eval e); print_newline ()
