
def successor(board, player, index):
    """
    Returns board if player played there or False if invalid move
    :param board: string board
    :param player: "X" or "O"
    :param index: where to put letter
    :return: string with successor move
    """
    return board[:index] + player + board[index+1:]


def legal_moves(board, player):
    """
    returns possible legal moves for player
    :param board: string board
    :param player: "X" or "O"
    :return: sequence of possible legal moves player can take
    """
    return tuple([i for i in range(len(board)) if board[i] == "."])


print(successor(".........", 'X', 0))
print(legal_moves("..XX..OOX", "X"))
