package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Board board = new Board();

        int fieldSchema[] = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Tak prezentuje sie rozklad pol na planszy. Przy podawaniu pol podczas ruchu prosze wskazywac pole wedlug nastepujacego schematu: ");
        for (int i = 0; i < fieldSchema.length; i++) {
            if (i == 2 || i == 5) {
                System.out.print(fieldSchema[i]);
                System.out.println();
                System.out.print("-----");
                System.out.println();
            } else {
                System.out.print(fieldSchema[i]);
                if (i != 8) {
                    System.out.print("|");
                }
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("Gra rozpoczyna sie, plansza jest pusta.");
        System.out.println();
        board.printBoard();
        System.out.println("-----------------");

        int moveCounter = 0;
        int computerMove = 0;

        /*while (true) {
         *//*int computerMove = 0;*//*
            board.playerMove();
            board.printBoard();
            moveCounter++;
            board.setMoveCount(moveCounter);
            board.checkPlayerWon();
            board.checkPlayer();
            if (board.isPlayerWin() == true) {
                break;
            }
            if (moveCounter < 9) {
                System.out.println("Komputer wykonuje ruch: ");
                board.setComputerMove(0);
                if (moveCounter <= 3) {
                    board.computerMove();
                } else {
                  board.checkComputer();
                }
                *//*computerMove++;
                board.setComputerMove(computerMove);*//*

                moveCounter++;
                board.setMoveCount(moveCounter);
                //board.checkComputer();
                board.checkComputerWon();
                board.printBoard();
                if (board.isComputerWin() == true) {
                    break;
                }
            } else if (moveCounter >= 9 && (board.isComputerWin() != true || board.isPlayerWin() != true)) {
                System.out.println("Nikt nie wygrał, jest remis!");
            }
        }*/

        while (true) {
            if (moveCounter < 9) {
                System.out.println("Ruch: " + board.getMoveCount());
                board.playerMove();
                board.printBoard();
                moveCounter++;
                board.setMoveCount(moveCounter);
                board.checkPlayerWon();
                if (board.isPlayerWin() == true) {
                    break;
                }
                if (board.computerMove() == false) {
                    if (board.checkComputer() == false) {
                        if (board.checkPlayer() == false) {
                            board.randomMove();
                        }
                    }
                }
                moveCounter++;
                board.printBoard();
                board.checkComputerWon();
                if (board.checkBoard() < 1) {
                    break;
                }
            }
        }

        //System.out.println("Gra zakonczona, plansza wypelniona. Remis!");
        System.out.println("Ilosc ruchow: " + moveCounter);
    }
}
