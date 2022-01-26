word = "knoll"
placements = [0, 0, 0, 0, 0]
for i in range(5):
    placements = [0, 0, 0, 0, 0]
    guess = input("Your Guess: ")
    if len(guess) != 5:
        print("Invalid Guess. Try again")
        i -= 1
    elif guess == word:
        print("Correct! Good Game")
        break
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

        print(placements)
        print("Try again")

print("You are out of guesses, you lose")
