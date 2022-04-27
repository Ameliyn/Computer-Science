import random


def get_rand_string(count):
    return "".join([random.choice(('0', '1')) for _ in range(count)])


print(get_rand_string(200))
