# Skye Russ Lubanovic 8: Dictionaries

# Standard 1
engSpanDict = {"one": "uno", "two": "dos", "three": "tres", "four": "cuatro", "five": "cinco",
               "six": "seis", "seven": "siete", "eight": "ocho", "nine": "nueve", "ten": "diez"}

# Standard 2
spanEngDict = {"uno": "one", "dos": "two", "tres": "three", "cuatro": "four", "cinco": "five", "seis": "six",
               "siete": "seven", "ocho": "eight", "nueve": "nine", "diez": "ten"}

# Standard 3
oddNumbers = {x for x in range(10) if x % 2 == 1}

# Standard 4
thing1 = ('optimist', 'pessimist', 'troll')
thing2 = ('The glass is half full', 'The glass is half empty', 'How did you get a glass?')
combined = dict(zip(thing1, thing2))

# Standard 5
titles = ['Creature of Habit', 'Crewel Fate', 'Sharks On a Plane']
plots = ['A nun turns into a monster', 'A haunted yarn shop', 'Check your exits']
movies = dict(zip(titles, plots))

