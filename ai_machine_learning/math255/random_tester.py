import random


def create_dataset(num_points, limit_a, limit_b, integer):
    """
    Creates and prints to a text file with number of points between a and b
    :param num_points: number of points to create
    :param limit_a: lower limit for dataset
    :param limit_b: upper limit for dataset
    :param integer: integer flag (1 for integers, 0 for doubles)
    :return:
    """
    # file_name = "python%d_%d_%d.txt" % (num_points, limit_a, limit_b)
    file_name = "pythondataset.txt"
    with open(file_name, "a") as f:
        f.write("py_%d_%d_%d = " % (num_points, limit_a, limit_b))
        for i in range(num_points):
            if integer == 1:
                f.write(str(random.randint(limit_a, limit_b)))
                f.write(", ")
                # print(random.randint(limit_a, limit_b), end=", ", file=f)
            else:
                f.write(str(random.random() * limit_b))
                f.write(", ")
                # print(random.random() * limit_b, end=", ", file=f)
        f.write("\n")
        # print(file=f)


def main():
    nums = 128, 512, 1024, 2048
    params = ((0, 1, 0), (1, 128, 1), (1, 512, 1), (1, 1024, 1), (1, 2048, 1))
    for limit_a, limit_b, integer in params:
        for n in nums:
            create_dataset(n, limit_a, limit_b, integer)


main()
