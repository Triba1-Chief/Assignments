def total_to_pay(bill, tax):
    return(round(bill + (bill * (tax / 100)), 2))
    
total_to_pay(100.0, 15.0)
total_to_pay(2.0, 13.0)
