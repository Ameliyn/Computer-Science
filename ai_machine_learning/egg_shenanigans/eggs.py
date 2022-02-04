# Base code to load a CSV file in Python
# Uses the
# @dr_tj

import csv

# Load rows into the eggs list. Each row is a dictionary storing the columns
with open('eggs_count.csv', newline='') as csvfile:
    egg_csv = csv.DictReader(csvfile)
    eggs = list(egg_csv)

# The keys for the rows are:
#   Chicken: Name of the chicken (Latte, Mocha, Peppa, Snowy)
#   Uncertain: Y if uncertain if chicken laid that egg, empty otherwise
#   Date: Date in MM/DD/YY format
#   High: High temperature in F
#   Low: Low temperature in F
#   Weather: Clear, Mostly Cloudy, Overcast, Rain
#   Breed: Americauna, Orpington
# All values, even the temperatures, are stored as strings from the CSV