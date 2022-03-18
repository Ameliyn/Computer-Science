from unittest import TestCase
from intro_neural import *
from numpy import *

all_inputs = np.array([[0, 0],
                       [0, 1],
                       [1, 0],
                       [1, 1]])


class Test(TestCase):

    # IMPORTANT: One of these tests will fail because no weight exist to compute that function!

    def test_and(self):
        correct = np.array([[0, 0, 0, 1]]).T
        self.assertTrue(np.all(correct == net_output(all_inputs, and_weights, and_bias)))

    def test_or(self):
        correct = np.array([[0, 1, 1, 1]]).T
        self.assertTrue(np.all(correct == net_output(all_inputs, or_weights, or_bias)))

    def test_nand(self):
        correct = np.array([[1, 1, 1, 0]]).T
        self.assertTrue(np.all(correct == net_output(all_inputs, nand_weights, nand_bias)))

    def test_xor(self):
        correct = np.array([[0, 1, 1, 0]]).T
        self.assertTrue(np.all(correct == net_output(all_inputs, xor_weights, xor_bias)))

    def test_nor(self):
        correct = np.array([[1, 0, 0, 0]]).T
        self.assertTrue(np.all(correct == net_output(all_inputs, nor_weights, nor_bias)))

