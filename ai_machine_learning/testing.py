
def tricky(a, *b, c):
    print(a)
    print(b)
    print(c)


def something(a,b,c):
    return a


def osomething(a, b, *c, f, **kwargs):
    print(kwargs)
    print(kwargs["d"])


