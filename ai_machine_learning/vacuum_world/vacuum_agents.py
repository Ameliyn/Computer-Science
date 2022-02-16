"""

Team 5: Simon Buan, Rosalee Hayes, Skye Russ, Anders Stall

"""
# Possible actions: "clean", "north", "east", "south", "west"
import random
from vacuum import *


world = [100][100]
current_xy = {50, 50}


def reflex_agent(space):
    if space is False:
        return "east"
    return "clean"


def random_agent(space):
    directions = ["north", "east", "south", "west"]
    if space is False:
        return random.choice(directions)
    else:
        return "clean"


def state_agent(space):
    if space is False:
        return "clean"
    return "east"


run(20, 50000, random_agent)

# print(many_runs(20, 50000, 10, random_agent))
