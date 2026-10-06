def weekly_pay(work_hours, hourly_wage):
    overtime_limit = 40
    overtime_factor = 1.5
 
    if work_hours > 40:
        overtime = work_hours - 40
        pay = (overtime_limit * hourly_wage) + (overtime * (hourly_wage * overtime_factor))
    else:
        pay = work_hours * hourly_wage
    
    return pay

print(weekly_pay(50, 12.5))
print(weekly_pay(30, 12.5))
print(weekly_pay(60, 15))
