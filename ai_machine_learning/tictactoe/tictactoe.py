def successor(board, player, index):
    """
    Returns board if player played there or False if invalid move
    :param board: string board
    :param player: "X" or "O"
    :param index: where to put letter
    :return: string with successor move
    """
    return board[:index] + player + board[index + 1:]


def legal_moves(board, player):
    """
    returns possible legal moves for player
    :param board: string board
    :param player: "X" or "O"
    :return: sequence of possible legal moves player can take
    """
    return tuple([i for i in range(len(board)) if board[i] == "."])


def winner(board):
    """
    Returns if the board has a winner, and who it is
    :param board: string current board state
    :return: 1 if "X" has won, -1 if "O" has won, or 0 otherwise
    """
    winning_lines = ((0, 1, 2), (3, 4, 5), (6, 7, 8), (0, 3, 6), (1, 4, 7), (2, 5, 8), (0, 4, 8), (2, 4, 6))
    for a, b, c in winning_lines:
        if board[a] == board[b] == board[c]:
            if board[a] == "X":
                return 1
            elif board[a] == "O":
                return -1
    return 0


print(successor(".........", 'X', 0))
print(legal_moves("..XX..OOX", "X"))
print(winner("X.X..X..X"))
