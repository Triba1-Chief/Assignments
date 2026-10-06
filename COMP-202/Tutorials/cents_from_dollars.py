def get_cents(bill):
    cents = (round(bill,2) * 100) % 100
    return((round(bill,2) * 100) % 100)

print(get_cents(1.237))  # 24
print(get_cents(0.05))  # 5
