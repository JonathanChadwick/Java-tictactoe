package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        String startingPlays = "_________";

        //Create initial 2D array and print initial board
        //board[3] = {countOfXs, countOfOs, null}
        String[][] board = createStartingBoard(startingPlays);
        printBoard(board);

        //Loop to prompt user for input and evaluate state of game

        int StateOfGame = 0;
        // player guide: 0 = O, 1 = X
        int player = 1;

        while (StateOfGame == 0 || StateOfGame == 4) {
            int [] coordinates = getInput(board);
            board = nextPlay(board, coordinates, player);
            int countOfXs = Integer.valueOf(board[3][0]);
            int countOfOs = Integer.valueOf(board[3][1]);
            printBoard(board);
            StateOfGame = checkStateOfGame(board, countOfOs, countOfXs);
            switch (player) {
                case 0:
                    player = 1;
                    break;
                case 1:
                    player = 0;
                    break;
            }
        }
        printStateOfGame(StateOfGame);

        }
    public static String[][] createStartingBoard(String input) {
        String[][] initialBoard = new String[4][3];
        int countOfOs = 0;
        int countOfXs = 0;
        int startLine = 0;
        for (int i = 0; i < initialBoard.length - 1; i++) {
            char[] row = new char[initialBoard[i].length];
            input.getChars(startLine, startLine + 3, row, 0);
            startLine += 3;
            for (int j = 0; j < initialBoard[i].length; j++) {
                initialBoard[i][j] = String.valueOf(row[j]);
                countOfOs = "O".equals(initialBoard[i][j]) ? ++countOfOs : countOfOs;
                countOfXs = "X".equals(initialBoard[i][j]) ? ++countOfXs : countOfXs;
            }
        }
        initialBoard[3][0] = Integer.toString(countOfXs);
        initialBoard[3][1] = Integer.toString(countOfOs);
        return initialBoard;
    }
    public static void printBoard(String[][] board) {
        System.out.println("---------");
        for (int i = 0; i < board.length - 1; i++) {
            System.out.print("| ");
            for (int j = 0; j < board[i].length; j++) {
                //board[i][j] = board[i][j] == null ? "_" : board[i][j];
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
        }

    public static int checkStateOfGame(String[][] board, int countOfOs, int countOfXs) {
        int winStates = 0;
        String winner = "";
        // Check for vertical and horizontal victory
        for (int i = 0; i < board.length - 1; i++) {
            if ("_".equals(board[i][0]) || "_".equals(board[0][i])) {
                continue;
            }
            if (board[0][i].equals(board[1][i]) && board[2][i].equals(board[0][i])) {
                winStates += 1;
                winner = board[0][i];
            }
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                winStates += 1;
                winner = board[i][0];
            }
        }

        //diagonal victory
        if (board[1][1].equals(board[0][0]) && board[1][1].equals(board[2][2]) && !"_".equals(board[1][1])) {
            winStates += 1;
            winner = board[1][1];
        }
        if (board[1][1].equals(board[0][2]) && board[1][1].equals(board[2][0]) && !"_".equals(board[1][1])) {
            winStates += 1;
            winner = board[1][1];
        }

        // return game state
        if (winStates > 1 || countOfOs - countOfXs > 1 || countOfXs - countOfOs > 1) { //Impossible state
            return 4;
        } else if (winStates == 1) {
            if ("X".equals(winner)) {
                return 1; // X wins
            } else return 2; // O wins
        } else if (winStates == 0 && countOfOs + countOfXs == 9) { //Draw
            return 3;
        } else {
            return 0; //Game not finished
        }

    }
    public static int[] getInput(String[][] board) {
        Scanner scanner = new Scanner(System.in);
        int[] coordinates = new int[2];
        boolean validInput = false;
        //System.out.println("Please provide valid coordinates for your next move (two numbers between 1 and 3): ");
        while (!validInput) {
            while (!scanner.hasNextInt()) {
                System.out.println("You should enter numbers!: ");
                scanner.nextLine();
            }
            coordinates[0] = scanner.nextInt() - 1;  // X Axis
            coordinates[1] = scanner.nextInt() - 1; // Y Axis
            if (coordinates[0] >= 0 && coordinates[0] <= 2 && coordinates[1] >= 0 && coordinates[1] <= 2) {
                if (!"_".equals(board[coordinates[0]][coordinates[1]])) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                validInput = true;
                return coordinates;
            }
            System.out.println("Coordinates should be from 1 to 3!");
        }
        return coordinates;
    }


    public static String[][] nextPlay(String[][] board, int[] coordinates, int player) {
        board[coordinates[0]][coordinates[1]] = player == 1 ? "X" : "O";
        if (player == 1) {
            board[3][0] = String.valueOf(Integer.valueOf(board[3][0]) + 1); //increment CountOfXs
        } else {
            board[3][1] = String.valueOf(Integer.valueOf(board[3][1]) + 1); //increment CountOfOs

        }
        return board;

    }

    public static void printStateOfGame(int state) {
        switch (state) {
            case 0:
                //System.out.println("Next player's turn!");
                break;
            case 1:
                System.out.println("X wins!");
                break;
            case 2:
                System.out.println("O wins!");
                break;
            case 3:
                System.out.println("Draw!");
                break;
            case 4:
                System.out.println("Impossible...");
                break;
        }

    }
}
