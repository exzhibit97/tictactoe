package com.company;

import java.util.Random;
import java.util.Scanner;


public class Board {
    private static int[][] possibleResults = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9},
            {1, 5, 9},
            {3, 5, 7},
    };
    private char[] fields = new char[]{'.', '.', '.', '.', '.', '.', '.', '.', '.'};
    private boolean playerWin = false;
    private boolean computerWin = false;
    private int moveCount = 0;
    private int computerMove = 0;


    public Board() {
    }

    public void printBoard() {
        for (int i = 0; i < fields.length; i++) {
            if (i == 2 || i == 5) {
                System.out.print(fields[i]);
                System.out.println();
                System.out.print("-----");
                System.out.println();
            } else {
                System.out.print(fields[i]);
                if (i != 8) {
                    System.out.print("|");
                }
            }
        }
        System.out.println();

    }

    public void playerMove() {
        Scanner playerInput = new Scanner(System.in);
        System.out.println("Podaj pole, ktore chcesz zaznaczyc: ");
        int sc = playerInput.nextInt();
        if (fields[sc - 1] != 'X' && fields[sc - 1] != 'O') {
            fields[sc - 1] = 'X';
        } else if (fields[sc - 1] == 'O') {
            System.out.println("Niepoprawny ruch, pole juz zaznaczone przez Komputer!");
            playerMove();
        } else if (fields[sc - 1] == 'X') {
            System.out.println("Niepoprawny ruch, pole juz zostalo wczesniej wybrane!");
            playerMove();
        } else {
            System.out.println("FATAL ERROR");
        }
        System.out.println();
    }

    public boolean computerMove() {
        boolean success = false;
        //System.out.println("Komputer wykonuje ruch: ");
        Random rand = new Random();
        int i = rand.nextInt(9);
        if (fields[i] == 'X') {
            System.out.println("W tym polu byl X, szukam innego. Chcialem postawic w polu: " + (i + 1));
            computerMove();
        } else if (fields[i] == 'O') {
            System.out.println("W tym polu bylo O, szukam innego. Chcialem postawic w polu: " + (i + 1));
            computerMove();
        } else if (moveCount == 1 && fields[4] == 'X') {
            int[] rogi = new int[]{0, 1, 6, 8};
            int w = rand.nextInt(4);
            System.out.println("Stawiam w rogu, jesli gracz rozpoczal na srodku. Wybieram pole: " + (w + 1));
            fields[rogi[w]] = 'O';
            success = true;
        } else if (moveCount == 1 && (fields[0] == 'X' || fields[2] == 'X' || fields[6] == 'X' || fields[8] == 'X')) {
            System.out.println("Stawiam na srodku, bo gracz rozpoczal na rogu");
            fields[4] = 'O';
            success = true;
        } else if (moveCount == 1 && (fields[1] == 'X' || fields[3] == 'X' || fields[5] == 'X' || fields[7] == 'X')) {
            fields[4] = 'O';
            success = true;
        } else if (moveCount == 3 && ((fields[0] == 'X' && fields[8] == 'X') || (fields[2] == 'X' && fields[6] == 'X'))) {
            if (i == 1 || i == 3 || i == 5 || i == 7) {
                fields[i] = 'O';
                success = true;
            } else {
                computerMove();
            }
        }
        return success;
    }

    public void checkPlayerWon() {
        int[] winningSet = new int[3];
        int countIxes;
        outerloop:
        for (int i = 0; i < 8; i++) {
            countIxes = 0;
            for (int j = 0; j < 3; j++) {
                int check = possibleResults[i][j];
                if (fields[check - 1] == 'X') {
                    countIxes++;
                    winningSet[j] = check;
                } else if (fields[check - 1] == '.') {
                    winningSet[j] = check + 100;
                }
                //System.out.println("count Player X'es: " + countIxes);
                if (countIxes == 3) {
                    System.out.println("You WON!");
                    setPlayerWin(true);
                    break outerloop;

                }
            }
        }
    }

    public void checkComputerWon() {
        int[] winningSet = new int[3];
        int countOs = 0;
        outerloop:
        for (int i = 0; i < 8; i++) {
            countOs = 0;
            for (int j = 0; j < 3; j++) {
                int check = possibleResults[i][j];
                if (fields[check - 1] == 'O') {
                    countOs++;
                    winningSet[j] = check;
                } else if (fields[check - 1] == '.') {
                    winningSet[j] = check + 100;
                }
                if (countOs == 3) {
                    System.out.println("Computer WON!");
                    setPlayerWin(true);
                    break outerloop;

                }
            }
        }
    }

    public boolean checkPlayer() {
        boolean success = false;
        int[] winningSet = new int[3];
        int[] temporary = new int[3];
        int countIxes = 0;
        outerloop:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                temporary[j] = possibleResults[i][j];
            }
            countIxes = 0;
            for (int j = 0; j < 3; j++) {
                int check = possibleResults[i][j];
                if (fields[check - 1] == 'X') {
                    countIxes++;
                    winningSet[j] = check;
                } else if (fields[check - 1] == '.') {
                    winningSet[j] = check + 100;
                }
                if (countIxes == 2) {
                    for (int k = 0; k < 3; k++) {
                        if (winningSet[0] == temporary[0]) {
                        } else {
                            System.out.println("Computer needs to block at field: " + (temporary[0]));
                            if (fields[temporary[0] - 1] == '.') {
                                fields[temporary[0] - 1] = 'O';
                                success = true;
                            }
                        }
                        if (winningSet[1] == temporary[1]) {
                        } else {
                            System.out.println("Computer needs to block at field: " + (temporary[1]));
                            if (fields[temporary[1] - 1] == '.') {
                                fields[temporary[1] - 1] = 'O';
                                success = true;
                            }
                        }
                        if (winningSet[2] == temporary[2]) {
                        } else {
                            System.out.println("Computer needs to block at field: " + (temporary[2]));
                            if (fields[temporary[2] - 1] == '.') {
                                fields[temporary[2] - 1] = 'O';
                                success = true;
                            }
                        }
                        int blockingCount = 0;

                        blockingCount++;
                        break outerloop;
                    }
                }
            }
        }
        return success;
    }

    public boolean checkComputer() {
        boolean success = false;
        int[] winningSet2 = new int[3];
        int[] temporary2 = new int[3];
        int countOs = 0;
        outerloop:
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 3; k++) {
                temporary2[k] = possibleResults[i][k];
            }
            countOs = 0;
            for (int s = 0; s < 3; s++) {
                int check = possibleResults[i][s];
                if (fields[check - 1] == 'O') {
                    countOs++;
                    winningSet2[s] = check;
                }
                //System.out.println("count Computer O's: " + countOs);
                if (countOs == 2) {
                    for (int k = 0; k < 3; k++) {
                        if (winningSet2[0] == temporary2[0]) {
                        } else {
                            if (fields[temporary2[0] - 1] == '.') {
                                System.out.println("Computer needs to attack at field: " + (temporary2[0]));
                                if (fields[temporary2[0] - 1] != 'X' && fields[temporary2[0] - 1] != 'O') {
                                    fields[temporary2[0] - 1] = 'O';
                                    success = true;
                                }
                            }
                        }
                        if (winningSet2[1] == temporary2[1]) {
                        } else {
                            if (fields[temporary2[1] - 1] == '.') {
                                System.out.println("Computer needs to attack at field: " + (temporary2[1]));
                                if (fields[temporary2[1] - 1] != 'X' && fields[temporary2[1] - 1] != 'O') {
                                    fields[temporary2[1] - 1] = 'O';
                                    success = true;
                                }
                            }
                        }
                        if (winningSet2[2] == temporary2[2]) {
                        } else {
                            if (fields[temporary2[2] - 1] == '.') {
                                System.out.println("Computer needs to attack at field: " + (temporary2[2]));
                                if (fields[temporary2[2] - 1] != 'X' && fields[temporary2[2] - 1] != 'O') {
                                    fields[temporary2[2] - 1] = 'O';
                                    success = true;
                                }
                            }
                        }
                        int blockingCount = 0;
                        /*if (winningSet[k] > 100 && blockingCount < 1) {
                            System.out.println("Computer needs to block at field: " + (winningSet[k] - 100));
                            fields[winningSet[k] - 101] = 'O';


                        }*/
                        blockingCount++;
                        break outerloop;
                    }
                }
                if (countOs == 3) {
                    System.out.println("Computer WON!");
                    setComputerWin(true);
                    break outerloop;
                }
            }
        }
        return success;
    }

    public void randomMove() {
        Random rand = new Random();
        int[] randomPossibilities = new int[9];
        int dotsCounter = 0;
        int i = 0;
        for (int j = 0; j < fields.length; j++) {
            if (fields[j] == '.') {
                dotsCounter++;
                //System.out.println("Computer can  make random move on field: " + (j + 1));
                randomPossibilities[i] = j;
                i++;
            }
        }
        int rnd = rand.nextInt(dotsCounter);
        fields[randomPossibilities[rnd]] = 'O';
    }

    public int checkBoard() {
        int dotsCounter = 0;
        for (int j = 0; j < fields.length; j++) {
            if (fields[j] == '.') {
                dotsCounter++;
            }

        }
        return dotsCounter;
    }


    public boolean isPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(boolean playerWin) {
        this.playerWin = playerWin;
    }

    public boolean isComputerWin() {
        return computerWin;
    }

    public void setComputerWin(boolean computerWin) {
        this.computerWin = computerWin;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public int getComputerMove() {
        return computerMove;
    }

    public void setComputerMove(int computerMove) {
        this.computerMove = computerMove;
    }
}

