# @author Skye Russ

"""
If you've already place all n queens, return the solution
Otherwise, for each row in the next column:
If you can solve the puzzle by placing the next queen in that row and then recurring, return that solution
If no solution was found, return False
"""


def check_space(limit: int, r: int, c: int, board: tuple):
    if r in board:
        return False
    if len(board) == 0:
        return True
    i = r - 1
    while i >= 0:
        if board[r - i] == c - i:
            return False
        if board[r - i] == c + i:
            return False
        i -= 1
    return True


def copy_tup(tup: tuple, n: int):
    cp = ()
    for i in tup:
        cp += i
    return cp


def find_solution(limit, c, queen_list):
    if limit == c:
        return queen_list
    for r in range(limit):
        if check_space(limit, r, c, queen_list):
            temp = find_solution(limit, c + 1, copy_tup(queen_list, c))
            if len(temp) <= 0 or temp[0] is False:
                continue
            else:
                return temp
    return False,


def nqueens(num):
    return find_solution(num, 0, ())
