(* GRADE:  100% *)
(** Exercise 1: Parse a Tree *)
let parse_tree_tests : (string * tree option) list = [
  ("", None);
  ("((. < 5 > .) < 4 > .)", Some (Node (Node (Leaf, 5, Leaf), 4, Leaf)))
]

let rec tree_parser i =
  let open Parser in
  let tree_parser_impl =
    first_of_2
      (const_map Leaf (symbol "."))
      ((** What should we do to parse a [Node] tree? *) 
        symbol "(" 
        |>> tree_parser
            |*> fun l -> symbol "<" 
                         |>> int_digits 
                             |*> fun v -> symbol ">"
                                          |>> tree_parser
                                              |*> fun r -> symbol ")"
                                                           |>> of_value(Node(l,v,r)) 
      )
  in
  tree_parser_impl i

(** DO NOT Change This Definition *)
let parse_tree : string -> tree option =
  let open Parser in
  run (between spaces eof tree_parser)

(** Part 1: Parse an Arithmetic Expression *)
let parse_arith_tests : (string * arith option) list = [
  ("", None);
  ("5 + 4", Some (Bop (Const 5, Plus, Const 4)))
]

let rec arith_parser i =
  let open Parser in
  let atomic_exp_parser =
    (** You may need to use [arith_parser] here *)
    first_of_2 
      (map (fun x -> Const x) int_digits)
      (between (symbol "(") (symbol ")") arith_parser) 
  in
  let power_exp_parser =
    right_assoc_op (symbol "^") atomic_exp_parser (fun l _ r -> Bop(l,Power,r)) 
  in
  let multiplicative_exp_parser =
    left_assoc_op (symbol "*") power_exp_parser (fun l _ r -> Bop(l,Times,r))
  in
  let arith_exp_parser_impl =
    let op_parser = 
      first_of [
        map(fun _ -> Plus) (symbol "+");
        map(fun _ -> Minus) (symbol "-")
      ]
    in
      
    left_assoc_op op_parser multiplicative_exp_parser (fun l o r -> Bop(l,o,r)) 
  in
  arith_exp_parser_impl i

(** DO NOT Change This Definition *)
let parse_arith : string -> arith option =
  let open Parser in
  run (between spaces eof arith_parser)
 