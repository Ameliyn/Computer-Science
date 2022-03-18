import numpy as np

and_weights = np.array([2, 2])
and_bias = np.array([-3])
or_weights = np.array([2, 2])
or_bias = np.array([-1])
nand_weights = np.array([-2, -2])
nand_bias = np.array([3])
nor_weights = np.array([-1, -1])
nor_bias = np.array([0])

# Exclusive or is impossible with our neural network formula because a positive offset creates or and a negative offset
# creates and
xor_weights = np.array([0, 0])
xor_bias = np.array([0])


def convert(x):
    if x >= 0:
        return 1
    return 0


def net_output(inputs: np.array, weights: np.array, bias: np.array):
    return np.asmatrix([convert(x) for x in np.matmul(inputs, weights) + bias]).T
