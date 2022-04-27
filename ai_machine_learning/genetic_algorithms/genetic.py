import random


def get_rand_string(count):
    return "".join([random.choice(('0', '1')) for _ in range(count)])


def determine_fitness(a, b):
    fitness = 0
    for x in range(len(a)):
        i = a[x]
        j = b[x]
        if i == j:
            fitness += 1
    return fitness


def determine_pop_fitness(count):
    target = get_rand_string(200)
    total = 0
    minimum = 201
    maximum = -1
    for _ in range(count):
        person = get_rand_string(200)
        temp = determine_fitness(person, target)
        total += temp
        if temp < minimum:
            minimum = temp
        if temp > maximum:
            maximum = temp
    print("Minimum: ", minimum)
    print("Maximum: ", maximum)
    print("Average: ", total/count)


print(determine_pop_fitness(1000))
