"""

Team 5: Simon Buan, Rosalee Hayes, Skye Russ, Anders Stall

"""
# Possible actions: "clean", "north", "east", "south", "west"
import random


def reflex_action(space):
    if space is True:
        return "east"
    return "clean"


def random_agent(space):
    directions = ["north", "east", "south", "west"]
    if space is True:
        return random.choice(directions)
    else:
        return "clean"


def state_agent(space):
    if space is False:
        return "clean"
    return "east"
