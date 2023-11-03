import java.io.*;
import java.util.*;

public class Othello {
    int turn;
    int winner;
    int board[][];
    // add required class variables here

    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        board = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
        // Student can choose to add preprocessing here
    }

    // add required helper functions here
    private void movee(int[][] boardd, int r, int c, int dir, int tt, int oo) {
        int rr, cc;
        switch (dir) {
            case 1:
                rr = -1;
                cc = 0;
                break;
            case 2:
                rr = -1;
                cc = 1;
                break;
            case 3:
                rr = 0;
                cc = 1;
                break;
            case 4:
                rr = 1;
                cc = 1;
                break;
            case 5:
                rr = 1;
                cc = 0;
                break;
            case 6:
                rr = 1;
                cc = -1;
                break;
            case 7:
                rr = 0;
                cc = -1;
                break;
            case 8:
                rr = 1;
                cc = -1;
                break;
            default:
                rr = 0;
                cc = 0;
        }
        boardd[r][c] = tt;
        r += rr;
        c += cc;
        while ((r >= 0) && (r < 8) && (c >= 0) && (c < 8)) {
            if (boardd[r][c] == -1) {
                break;
            } else if (boardd[r][c] == tt) {
                break;
            }
            boardd[r][c] = tt;
            r += rr;
            c += cc;
        }
    }

    private void move(int[][] boardd, int r, int c, int dir, int tt, int oo) {
        int r1, c1;
        boardd[r][c] = tt;
        // dir1 top
        if (dir == 1) {
            r1 = r - 1;
            c1 = c;
            if (r1 >= 0 && boardd[r1][c1] == oo) {
                boardd[r1][c1] = tt;
                r1--;
                while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                    if (boardd[r1][c1] == -1) {
                        break;
                    } else if (boardd[r1][c1] == tt) {
                        break;
                    }
                    boardd[r1][c1] = tt;
                    r1--;
                }
            }
        }
        // dir2 top right
        if (dir == 2) {
            r1 = r - 1;
            c1 = c + 1;
            if (r1 >= 0 && c1 < 8 && boardd[r1][c1] == oo) {
                boardd[r1][c1] = tt;
                r1--;
                c1++;
                while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                    if (boardd[r1][c1] == -1) {
                        break;
                    } else if (boardd[r1][c1] == tt) {
                        // moves.add(2);
                        break;
                    }
                    boardd[r1][c1] = tt;
                    r1--;
                    c1++;
                }
            }
        }
        // dir 3 right
        if (dir == 3) {
            r1 = r;
            c1 = c + 1;
            if (c1 < 8 && boardd[r1][c1] == oo) {
                boardd[r1][c1] = tt;
                c1++;
                while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                    if (boardd[r1][c1] == -1) {
                        break;
                    } else if (boardd[r1][c1] == tt) {
                        // moves.add(3);
                        break;
                    }
                    boardd[r1][c1] = tt;
                    c1++;
                }
            }
        }
        // dir 4 right bottom
        if (dir == 4) {
            r1 = r + 1;
            c1 = c + 1;
            if (r1 < 8 && c1 < 8 && boardd[r1][c1] == oo) {
                boardd[r1][c1] = tt;
                r1++;
                c1++;
                while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                    if (boardd[r1][c1] == -1) {
                        break;
                    } else if (boardd[r1][c1] == tt) {
                        // moves.add(4);
                        break;
                    }
                    boardd[r1][c1] = tt;
                    r1++;
                    c1++;
                }
            }
        }
        // dir 5 bottom
        if (dir == 5) {
            r1 = r + 1;
            c1 = c;
            if (r1 < 8 && boardd[r1][c1] == oo) {
                boardd[r1][c1] = tt;
                r1++;

                while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                    if (boardd[r1][c1] == -1) {
                        break;
                    } else if (boardd[r1][c1] == tt) {
                        // moves.add(5);
                        break;
                    }
                    boardd[r1][c1] = tt;
                    r1++;

                }
            }
        }

        // dir 6 bottom left
        if (dir == 6) {
            r1 = r + 1;
            c1 = c - 1;
            if (r1 < 8 && c1 >= 0 && boardd[r1][c1] == oo) {
                // System.out.println("dir 6 " + r1 + " " + c1);
                boardd[r1][c1] = tt;
                r1++;
                c1--;
                while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                    if (boardd[r1][c1] == -1) {
                        break;
                    } else if (boardd[r1][c1] == tt) {
                        // moves.add(6);
                        break;
                    }
                    boardd[r1][c1] = tt;
                    r1++;
                    c1--;
                }
            }
        }
        // dir 7 left
        if (dir == 7) {
            r1 = r;
            c1 = c - 1;
            if (c1 >= 0 && boardd[r1][c1] == oo) {
                boardd[r1][c1] = tt;
                c1--;
                while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                    if (boardd[r1][c1] == -1) {
                        break;
                    } else if (boardd[r1][c1] == tt) {
                        // moves.add(7);
                        break;
                    }
                    boardd[r1][c1] = tt;
                    c1--;
                }
            }
        }
        // dir 8 left top
        if (dir == 8) {
            r1 = r - 1;
            c1 = c - 1;
            if (r1 >= 0 && c1 >= 0 && boardd[r1][c1] == oo) {
                boardd[r1][c1] = tt;
                r1--;
                c1--;
                while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                    if (boardd[r1][c1] == -1) {
                        break;
                    } else if (boardd[r1][c1] == tt) {
                        // moves.add(8);
                        break;
                    }
                    boardd[r1][c1] = tt;
                    r1--;
                    c1--;
                }
            }
        }
        // return moves;
    }

    private ArrayList<Integer> check(int[][] boardd, int r, int c, int tt, int oo) {
        ArrayList<Integer> moves = new ArrayList<Integer>();
        int r1, c1;
        // dir1 top
        r1 = r - 1;
        c1 = c;
        if (r1 >= 0 && boardd[r1][c1] == oo) {
            r1--;
            while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                if (boardd[r1][c1] == -1) {
                    break;
                } else if (boardd[r1][c1] == tt) {
                    moves.add(1);
                    break;
                }
                r1--;
            }
        }
        // dir2 top right
        r1 = r - 1;
        c1 = c + 1;
        if (r1 >= 0 && c1 < 8 && boardd[r1][c1] == oo) {
            r1--;
            c1++;
            while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                if (boardd[r1][c1] == -1) {
                    break;
                } else if (boardd[r1][c1] == tt) {
                    moves.add(2);
                    break;
                }
                r1--;
                c1++;
            }
        }
        // dir 3 right
        r1 = r;
        c1 = c + 1;
        if (c1 < 8 && boardd[r1][c1] == oo) {

            c1++;
            while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                if (boardd[r1][c1] == -1) {
                    break;
                } else if (boardd[r1][c1] == tt) {
                    moves.add(3);
                    break;
                }

                c1++;
            }
        }
        // dir 4 right bottom
        r1 = r + 1;
        c1 = c + 1;
        if (r1 < 8 && c1 < 8 && boardd[r1][c1] == oo) {
            r1++;
            c1++;
            while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                if (boardd[r1][c1] == -1) {
                    break;
                } else if (boardd[r1][c1] == tt) {
                    moves.add(4);
                    break;
                }
                r1++;
                c1++;
            }
        }
        // dir 5 bottom
        r1 = r + 1;
        c1 = c;
        if (r1 < 8 && boardd[r1][c1] == oo) {
            r1++;

            while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                if (boardd[r1][c1] == -1) {
                    break;
                } else if (boardd[r1][c1] == tt) {
                    moves.add(5);
                    break;
                }
                r1++;

            }
        }
        // dir 6 bottom left
        r1 = r + 1;
        c1 = c - 1;
        if (r1 < 8 && c1 >= 0 && boardd[r1][c1] == oo) {
            // System.out.println("dir 6 " + r1 + " " + c1);
            r1++;
            c1--;
            while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                if (boardd[r1][c1] == -1) {
                    break;
                } else if (boardd[r1][c1] == tt) {
                    moves.add(6);
                    break;
                }
                r1++;
                c1--;
            }
        }
        // dir 7 left
        r1 = r;
        c1 = c - 1;
        if (c1 >= 0 && boardd[r1][c1] == oo) {

            c1--;
            while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                if (boardd[r1][c1] == -1) {
                    break;
                } else if (boardd[r1][c1] == tt) {
                    moves.add(7);
                    break;
                }

                c1--;
            }
        }
        // dir 8 left top
        r1 = r - 1;
        c1 = c - 1;
        if (r1 >= 0 && c1 >= 0 && boardd[r1][c1] == oo) {
            r1--;
            c1--;
            while ((r1 >= 0) && (r1 < 8) && (c1 >= 0) && (c1 < 8)) {
                if (boardd[r1][c1] == -1) {
                    break;
                } else if (boardd[r1][c1] == tt) {
                    moves.add(8);
                    break;
                }
                r1--;
                c1--;
            }
        }
        return moves;
    }

    private int helper_boardcore(int[][] boarddd) {
        int b = 0;
        int w = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boarddd[i][j] == 1) {
                    w += 1;
                } else if (boarddd[i][j] == 0) {
                    b += 1;
                }
            }
        }
        if (turn == 1) {
            return w - b;
        } else {
            return b - w;
        }
    }

    public int boardScore() {
        /*
         * Complete this function to return num_black_tiles - num_white_tiles if turn =
         * 0,
         * and num_white_tiles-num_black_tiles otherwise.
         */
        return helper_boardcore(board);
    }

    private int[] helperminmax(int[] score, int i, int j, int boardscore, int minmax) {
        if (minmax == 0) {
            if (boardscore < score[0]) {
                score[0] = boardscore;
                score[1] = i * 8 + j;
                score[2] = i;
                score[3] = j;
            }
            // } else if (boardscore == score[0]) {
            // if (score[1] > i * 8 + j) {
            // score[1] = i * 8 + j;
            // score[2] = i;
            // score[3] = j;
            // }
            // }
        } else {
            if (boardscore > score[0]) {
                score[0] = boardscore;
                score[1] = i * 8 + j;
                score[2] = i;
                score[3] = j;
            }
            // else if (boardscore == score[0]) {
            // if (score[1] > i * 8 + j) {
            // score[1] = i * 8 + j;
            // score[2] = i;
            // score[3] = j;
            // }
            // }
        }
        return score;
    }

    private int[] helperbestmove(int[][] boardd, int minmax, int k, int tt, int oo) {
        ArrayList<Integer> temp;
        int[] score = new int[4];
        if (minmax == 0) {
            score[0] = Integer.MAX_VALUE;
        } else {
            score[0] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardd[i][j] == -1) {
                    temp = check(boardd, i, j, tt, oo);
                    if (temp.size() == 0) {
                        // System.out.println("emp");
                        continue;
                    }
                    int[][] temp_board = getcopy(boardd);
                    for (int l : temp) {
                        move(temp_board, i, j, l, tt, oo);
                    }
                    int absbs = (int) i * 8 + (int) j;
                    if (k == 4)
                        System.out.println(
                                i + " " + j + " " + absbs + " " + temp + " k- " + k + " " + minmax);
                    // pprin(temp_board);
                    if (k == 1) {
                        if (minmax == 0) {
                            score = helperminmax(score, i, j, helper_boardcore(temp_board), minmax);
                        } else {
                            score = helperminmax(score, i, j, helper_boardcore(temp_board), minmax);
                        }
                        // score=helperminmax(score, i, j, oo, minmax);
                        // return ;
                    } else {
                        // pprin(temp_board);
                        if (minmax == 0) {
                            int scorefromk = helperbestmove(temp_board, 1, k - 1, oo, tt)[0];
                            if (scorefromk != Integer.MAX_VALUE && scorefromk != Integer.MIN_VALUE) {
                                score = helperminmax(score, i, j, scorefromk, minmax);
                            } else {
                                score = helperminmax(score, i, j, helper_boardcore(temp_board), minmax);
                            }
                        } else {
                            int scorefromk = helperbestmove(temp_board, 0, k - 1, oo, tt)[0];
                            if (scorefromk != Integer.MAX_VALUE && scorefromk != Integer.MIN_VALUE) {
                                score = helperminmax(score, i, j, scorefromk, minmax);
                            } else {
                                score = helperminmax(score, i, j, helper_boardcore(temp_board), minmax);
                            }
                        }
                    }
                }
            }
        }
        // System.out.println("k " + k + " " + score[0] + " " + score[2] + " " +
        // score[3]);
        return score;
    }

    public int bestMove(int k) {
        /*
         * Complete this function to build a Minimax tree of depth k (current board
         * being at depth 0),
         * for the current player (siginified by the variable turn), and propagate
         * scores upward to find
         * the best move. If the best move (move with max score at depth 0) is i,j;
         * return i*8+j
         * In case of ties, return the smallest integer value representing the tile with
         * best score.
         * 
         * Note: Do not alter the turn variable in this function, so that the
         * boardScore() is the score
         * for the same player throughout the Minimax tree.
         */
        int score[];
        if (turn == 0) {
            score = helperbestmove(board, 1, k, 0, 1);
        } else {
            score = helperbestmove(board, 1, k, 1, 0);
        }
        System.out.println(score[2] + " " + score[3]);
        return score[1];
    }

    public ArrayList<Integer> fullGame(int k) {
        /*
         * Complete this function to compute and execute the best move for each player
         * starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and
         * the turn
         * at each step. In the end, modify the winner variable as required.
         */
        // System.out.println();
        int bound = 0;
        int score[];
        ArrayList<Integer> moves = new ArrayList<Integer>();
        // int i = 0;
        while (true) {
            if (turn == 0) {
                score = helperbestmove(board, 1, k, 0, 1);
                if (score[0] == Integer.MAX_VALUE || score[0] == Integer.MIN_VALUE) {
                    turn = 1;
                    bound += 1;
                } else {
                    moveboard(score[2], score[3], 0, 1);
                    moves.add(score[1]);
                    bound = 0;
                    turn = 1;
                }
            } else {
                score = helperbestmove(board, 1, k, 1, 0);
                if (score[0] == Integer.MAX_VALUE || score[0] == Integer.MIN_VALUE) {
                    turn = 0;
                    bound += 1;
                } else {
                    moveboard(score[2], score[3], 1, 0);
                    moves.add(score[1]);
                    bound = 0;
                    turn = 0;
                }
            }
            if (bound == 2) {
                break;
            }
            // i++;
            System.out.println(turn + " " + moves.get(moves.size() - 1));
            pprin(board);
        }
        int ss = boardScore();
        if (turn == 1) {
            if (ss > 0) {
                winner = 1;
            } else {
                winner = 0;
            }
        } else {
            if (ss < 0) {
                winner = 1;
            } else {
                winner = 0;
            }
        }
        return moves;
    }

    private int[][] getcopy(int[][] boardd) {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(boardd[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }

    private void moveboard(int i, int j, int tt, int oo) {
        // temp = check(boardd, i, j, tt, oo);
        // if (temp.size() == 0) {
        // continue;
        // }
        // int[][] temp_board = getBoardCopy();
        ArrayList<Integer> abcd = check(board, i, j, tt, oo);
        for (int l : abcd) {
            move(board, i, j, l, tt, oo);
        }
    }

    private void pprin(int[][] boardd) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(boardd[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            Othello obj = new Othello("input.txt");
            System.out.println(obj.boardScore());
            System.out.println(obj.bestMove(4));
            // obj.test(6, 5, 0, 1);
            // System.out.println(obj.check(obj.getBoardCopy(), 2, 4, 0, 1));
            // obj.move(obj.getBoardCopy(), 2, 4, 5, 0, 1);
            // for (int i = 0; i < 8; i++) {
            // for (int j = 0; j < 8; j++) {
            // System.out.print(obj.board[i][j] + "\t");
            // }
            // System.out.println();
            // }
            // System.out.println(obj.fullGame(4));
            // obj
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}