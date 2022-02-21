
def initialize_board(num):
    temp = [num][num]
    for i in range(num):
        for j in range(num):
            temp[i][j] = 0
    return temp


def check_space(num, x, y, board):
    for i in range(num):
        if board[i][y] == 1:
            return False
        if board[x][i] == 1:
            return False
        if x+i < num and y+i < num and board[x+1][y+i] == 1:
            return False
        if x-i >= 0 and y-i >= 0 and board[x-1][y-i] == 1:
            return False
        if x+i < num and y-i >= 0 and board[x+1][y-i] == 1:
            return False
        if x-i >= 0 and y+i < num and board[x-1][y+i] == 1:
            return False
    return True


def nqueens(num):
    board = initialize_board(num)

    return False
