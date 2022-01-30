import random

legal_words = []
mystery_words = []

with open("wordle/legal_words.txt") as f:
    for line in f:
        legal_words.append(line.strip())

with open("wordle/mystery_words.txt") as f:
    for line in f:
        mystery_words.append(line.strip())

word = random.choice(mystery_words)
placements = [0, 0, 0, 0, 0]

for i in range(6):
    placements = [0, 0, 0, 0, 0]
    guess = input("Your Guess: ")
    if len(guess) != 5 or guess not in legal_words:
        print("Invalid Guess. Try again")
        i -= 1
    elif guess == word:
        print("Correct! Good Game")
        exit()
    else:
        temp = ""
        for j in range(5):
            if word[j] != guess[j]:
                temp += word[j]

        for j in range(5):
            if word[j] == guess[j]:
                placements[j] = 2
            elif guess[j] in temp:
                placements[j] = 1
                temp = temp.replace(guess[j], "", 1)

        temp = ""
        for j in range(5):
            if placements[j] == 1:
                temp += "?"
            elif placements[j] == 2:
                temp += "!"
            else:
                temp += "."

        print(temp)
        print("Try again")


print("You lose. The word was", word)