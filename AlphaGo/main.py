"""
Used many references from Homework 4 for this assignment, as the algorithms we used were the same as in that assignment
https://stackabuse.com/minimax-and-alpha-beta-pruning-in-python/#conclusion as a reference for alpha-beta search, validity of moves, 
end of game scenarios.
https://www.youtube.com/@chessprogramming591 / https://github.com/maksimKorzh/wally/blob/main/wally.py for reference
of other types of GO-games being my in Python
https://www.moderndescartes.com/essays/implementing_go/ and their GitHub: https://github.com/brilee/go_implementation for 
partial understanding of liberties and getting neighbors for GO games.
https://www.usgo.org/rules-of-go : Rules of GO
https://github.com/jojuno/ai_go/blob/main/my_player3_backup.py#L612
"""
import numpy as np
import time
from bigO import BigO

PLAYER_1 = 1
PLAYER_2 = -1
board = np.zeros((6, 6), int)
# print(board)
# The player whose turn it is. 1 for player 1, -1 for player 2.
player = 0
def init_board(self, n):
    self.board = board
    
def has_won(board, player):
    # Check if the opponent has no legal moves left
    if len(possible_moves(board)) == 0:
        return True
    
    # Check if the player has captured all of the opponent's stones
    if np.sum(board == -player) == 0:
        return True
    
    # Otherwise, the player has not won
    return False

def game_over(board):
    # Check if the board is full
    if np.all(board != 0):
        return True
    # Check if either player has won
    if has_won(board, 1) or has_won(board, -1):
        return True
    # Otherwise, the game is not over
    return False

def possible_moves(board):
    moves = []
    for i in range(board.shape[0]):
        for j in range(board.shape[1]):
            if board[i, j] == 0:
                moves.append((i, j))
    return moves

def make_move(board, position, player):
    x, y = position
    board[x][y] = player
    if player == 1:
        board[x, y] == 1
    elif player == -1:
        board[x, y] == -1
    # board[x, y] = player
    
def minimax(board, player, depth):
    # Check if the game is over (someone has won or the board is full) or if we have reached the maximum depth
    if depth == 0 or game_over(board):
        # Return the heuristic value of the board
        return heuristic(board)
    
    # If it's player 1's turn, we want to maximize the heuristic value
    if player == 1:
        best_value = float("-inf")
        best_move = None
        for move in possible_moves(board):
            # Make the move on a copy of the board
            new_board = np.copy(board)
            make_move(new_board, move, player)
        if depth == depth.max:
           return depth
        for i in range(board.size):
            for j in range(board.size):
                if board[i][j] != 1:
                    move += 1
            # Update the best value and move if necessary
            if value > best_value:
                best_value = value
                best_move = move
        # Update the board state with the best move
        make_move(board, best_move, player)
        # Return the best move and value
        return best_move, best_value
    # If it's player 2's turn, we want to minimize the heuristic value
    else:
        best_value = float("inf")
        best_move = None
        for move in possible_moves(board):
            # Make the move on a copy of the board
            new_board = np.copy(board)
            make_move(new_board, move, player)
            # Recursively call the minimax function on the new board state
            value = minimax(new_board, -player, depth - 1)
            # Update the best value and move if necessary
            if value < best_value:
                best_value = value
                best_move = move
            # Update the beta value
            beta = min(beta, best_value)
            # Check if we can prune the search tree
            if beta <= alpha:
                break
        # Update the board state with the best move
        make_move(board, best_move, player)
        # Return the best move and value
        return best_move, best_value
      
        

    
# The Alpha-Beta pruning function. This function takes in the current board state, the player whose turn it is,
# the alpha and beta values, and the depth of the search tree. It returns the best move for the current player.
def alpha_beta_pruning(board, player, alpha, beta, depth):
    # Check if the game is over (someone has won or the board is full) or if we have reached the maximum depth
    if depth == 0 or game_over(board):
        # Return the heuristic value of the board
        return heuristic(board)
    
    # If it's player 1's turn, we want to maximize the heuristic value
    if player == 1:
        best_value = float("-inf")
        best_move = None
        for move in possible_moves(board):
            # Make the move on a copy of the board
            print_avg_time_complexity(board, player, depth)
            new_board = np.copy(board)
            make_move(new_board, move, player)
            # Recursively call the Alpha-Beta function on the new board state
            value = alpha_beta_pruning(new_board, -player, alpha, beta, depth - 1)
            # Update the best value and move if necessary
            if value > best_value:
                best_value = value
                best_move = move
            # Update the alpha value
            alpha = max(alpha, best_value)
            # Check if we can prune the search tree
            if beta <= alpha:
                break
        # Update the board state with the best move
        make_move(board, best_move, player)
        # Return the best move and value
        return best_move, best_value
    # If it's player 2's turn, we want to minimize the heuristic value
    else:
        best_value = float("inf")
        best_move = None
        for move in possible_moves(board):
            # Make the move on a copy of the board
            new_board = np.copy(board)
            make_move(new_board, move, player)
            # Recursively call the Alpha-Beta function on the new board state
            value = alpha_beta_pruning(new_board, -player, alpha, beta, depth - 1)
            # Update the best value and move if necessary
            if value < best_value:
                best_value = value
                best_move = move
            # Update the beta value
            beta = min(beta, best_value)
             # Check if we can prune the search tree
            if beta <= alpha:
                break
        # Update the board state with the best move
        make_move(board, best_move, player)
        # Return the best move and value
        return best_move, best_value

# The heuristic function. This function takes in a board state and returns a heuristic value for the board.
# The heuristic value should be high if the board is favorable for player 1 and low if the board is favorable
def heuristic(board):
    # Calculate the number of stones for each player
    num_stones_player_1 = np.sum(board == 1)
    num_stones_player_2 = np.sum(board == -1)
    
    # Calculate the number of liberties for each player
    num_liberties_player_1 = 0
    num_liberties_player_2 = 0
    for i in range(board.shape[0]):
        for j in range(board.shape[1]):
            if board[i, j] == 1:
                num_liberties_player_1 += liberties(board, (i, j))
            elif board[i, j] == -1:
                num_liberties_player_2 += liberties(board, (i, j))
    
    # Calculate the territory controlled by each player
    territory_player_1 = 0
    territory_player_2 = 0
    for i in range(board.shape[0]):
        for j in range(board.shape[1]):
            if board[i, j] == 0:
                if is_territory_player_1(board, (i, j)):
                    territory_player_1 += 1
                elif is_territory_player_2(board, (i, j)):
                    territory_player_2 += 1
    
    # Calculate the potential for each player to capture the other player's stones
    capture_potential_player_1 = 0
    capture_potential_player_2 = 0
    for i in range(board.shape[0]):
        for j in range(board.shape[1]):
            if board[i, j] == -1:
                capture_potential_player_1 += can_capture(board, (i, j), 1)
            elif board[i, j] == 1:
                capture_potential_player_2 += can_capture(board, (i, j), -1)
    
    # Combine all of these factors to create the final heuristic value
    heuristic_value = num_stones_player_1 + num_liberties_player_1 + territory_player_1 + capture_potential_player_2
    return heuristic_value

def liberties(board, position):
    """Returns the number of liberties for the stone at the given position on the board."""
    i, j = position
    num_liberties = 0
    # Check the top, bottom, left, and right positions for empty spaces
    if i > 0 and board[i-1, j] == 0:
        num_liberties += 1
    if i < board.shape[0]-1 and board[i+1, j] == 0:
        num_liberties += 1
    if j > 0 and board[i, j-1] == 0:
        num_liberties += 1
    if j < board.shape[1]-1 and board[i, j+1] == 0:
        num_liberties += 1
    return num_liberties

def is_territory_player_1(board, position):
    """Returns True if the given position on the board is territory controlled by player 1, False otherwise."""
    i, j = position
    if board[i, j] != 0:
        return False
    # Check the top, bottom, left, and right positions for player 1's stones
    if i > 0 and board[i-1, j] == 1:
        return True
    if i < board.shape[0]-1 and board[i+1, j] == 1:
        return True
    if j > 0 and board[i, j-1] == 1:
        return True
    if j < board.shape[1]-1 and board[i, j+1] == 1:
        return True
    return False

def is_territory_player_2(board, position):
    """Returns True if the given position on the board is territory controlled by player 2, False otherwise."""
    i, j = position
    if board[i, j] != 0:
        return False
    # Check the top, bottom, left, and right positions for player 2's stones
    if i > 0 and board[i-1, j] == -1:
        return True
    if i < board.shape[0]-1 and board[i+1, j] == -1:
        return True
    if j > 0 and board[i, j-1] == -1:
        return True
    if j < board.shape[1]-1 and board[i, j+1] == -1:
        return True
    return False

def can_capture(board, position, player):
    """Returns the number of opponent's stones that can be captured by the player at the given position on the board."""
    i, j = position
    if board[i, j] != player:
        return 0
    num_captures = 0
    # Check the top, bottom, left, and right positions for the opponent's stones
    if i > 0 and board[i-1, j] == -player and liberties(board, (i-1, j)) == 0:
        num_captures += 1
    if i < board.shape[0]-1 and board[i+1, j] == -player and liberties(board, (i+1, j)) == 0:
        num_captures += 1
    if j > 0 and board[i, j-1] == -player and liberties(board, (i, j-1)) == 0:
        num_captures += 1
    if j < board.shape[1]-1 and board[i, j+1] == -player and liberties(board, (i, j+1)) == 0:
        num_captures += 1
    return num_captures

def print_board(board):
    """Prints the board with player 1's stones represented by 'X' and player 2's stones represented by 'O'."""
    for i in range(board.shape[0]):
        row = []
        for j in range(board.shape[1]):
            if board[i, j] == 1:
                row.append("X")
            elif board[i, j] == -1:
                row.append("O")
            else:
                row.append("0")
        print("|".join(row))

def play_game(board, player1, player2, turn):
    """Plays the game and prints the final result."""
    
    while not game_over(board):
        new_board = []
        # Use Alpha-Beta pruning to determine the best move
        if turn == PLAYER_1:
            move, _ = alpha_beta_pruning(board, player, float("-inf"), float("inf"), 3)
            make_move(board, move, player)
            turn = PLAYER_2
        else:
            move, _ = minimax(board, player, 3)
            make_move(board, move, player)
            turn = PLAYER_1
    # Print the final result of the game
    print(board)

    if has_won(board, 1):
        print("Player 1 has won!")
    elif has_won(board, -1):
        print("Player 2 has won!")
    else:
        print("The game was a draw.")

def print_avg_time_complexity(board, player, depth):
    start_time = time.time()
    alpha_beta_pruning(board, player, float("-inf"), float("inf"), depth)
    end_time = time.time()
    elapsed_time = end_time - start_time
    print(f"Average time complexity per move: {elapsed_time / depth:.5f} seconds")

def died_pieces(self, piece_type, position):
    died_pieces = []
    for i in range(len(board)):
        for j in range(len(board)):
            if board[i][j] == piece_type:
                died_pieces.append((i,j))
    died_pieces = self.died_pieces(piece_type)
    if not died_pieces: return []
    self.remove_certain_pieces(died_pieces)
    return died_pieces

    for piece in positions:
        board[piece[0]][piece[1]] = 0
    self.update_board(board)
    
def update_board(self, new_board):
    self.board = new_board
    
if __name__ == "__main__":
    # print_board(board)
    play_game(board, alpha_beta_pruning, minimax, -1)
