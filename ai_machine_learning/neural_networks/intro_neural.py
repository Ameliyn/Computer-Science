import numpy as np
# all_inputs = np.array([[0, 0],
#                        [0, 1],
#                        [1, 0],
#                        [1, 1]])
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


def net_output(inputs: np.array, weights: np.array, bias: np.array):
    return np.asmatrix([1 if x >= 0 else 0 for x in np.matmul(inputs, weights) + bias]).T


# Advanced 1
and_weights_bias = np.array([2, 2, -3])
or_weights_bias = np.array([2, 2, -1])
nand_weights_bias = np.array([-2, -2, 3])
nor_weights_bias = np.array([-1, -1, 0])


def net_output_advanced1(inputs: np.array, weights: np.array):
    return np.asmatrix([1 if x >= 0 else 0 for x in np.matmul(np.insert(inputs, 2, 1, axis=1), weights)]).T


# Advanced 2
mega_matrix = np.array([and_weights_bias, or_weights_bias, nand_weights_bias, nor_weights_bias])


def net_output_advanced2(inputs: np.array, weights: np.array):
    return np.asmatrix([[1 if x >= 0 else 0 for x in np.matmul(np.insert(inputs, 2, 1, axis=1), weight)] for weight in weights]).T
