"""

Team 5: Simon Buan, Rosalee Hayes, Skye Russ, Anders Stall

"""
# Possible actions: "clean", "north", "east", "south", "west"
import random
from vacuum import *


world = [100][100]
current_xy = {50, 50}
move_queue = []


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

    """
    Plan: 
    0. Try to map out the four outer walls
    1. Create a move queue, if it's empty try to move east, then north, then west, then south
    2. If not empty, execute the move queue.
    3. If tried to move east, and still on clean (hit wall), try to go over by the north, then try south.
    3a. if still cant go east, go north one space and then go back west (repeat).
    4a. after repeating, try to do south half of the board
    """
    return "east"


run(20, 50000, random_agent)

# print(many_runs(20, 50000, 10, random_agent))
