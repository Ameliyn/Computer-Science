# @author Skye Russ

"""
If you've already placed all n queens, return the solution
Otherwise, for each row in the next column:
If you can solve the puzzle by placing the next queen in that row and then recurring, return that solution
If no solution was found, return False
"""


def check_space(r: int, c: int, board: tuple):
    if r in board:  # if row already taken
        return False
    i = 1  # start at 1 less than the current row
    while i <= c:
        if c - i >= 0 and (board[c - i] == r - i or board[c - i] == r + i):  # check if column before available and if queen in diagonals
            return False
        i += 1
    return True


def find_solution(limit, c, queen_list):
    for r in range(limit):
        if check_space(r, c, queen_list):
            if limit == c+1:  # if on last row
                temp = queen_list + (r,)
            else:
                temp = find_solution(limit, c + 1, (queen_list + (r,)))
            if not temp:  # if recursive call comes back false, try the next row
                continue
            else:
                return temp
    return False


def nqueens(num):
    return find_solution(num, 0, ())
