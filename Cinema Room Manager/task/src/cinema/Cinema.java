package cinema;
import javax.swing.*;
import java.util.Scanner;
import java.util.Arrays;
public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] rowsSeats = getCinemaRoom(scanner);
        int ticketsSold = 0, currentIncome = 0;
        int rows = rowsSeats[0], seats = rowsSeats[1];
        int[][] cinemaMatrix = createCinemaRoom(rows, seats);
        while(true){
            askUser();
            int userInput = scanner.nextInt();
            switch(userInput){
                case 3:
                    printStatistics(rows * seats, (float) ticketsSold, currentIncome, getPossibleIncome(cinemaMatrix));
                case 1:
                    printCinemaRoom(cinemaMatrix);
                    break;
                case 2:
                    currentIncome += buyTicket(cinemaMatrix, scanner);
                    ticketsSold += 1;
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void printStatistics(int maxCapacity, float ticketsSold, int currentIncome, int totalIncome) {
        System.out.printf("Number of purchased tickets: %d\n", (int) ticketsSold);
        System.out.printf("Percentage: %.2f", (ticketsSold * 100) / maxCapacity);
        System.out.println("%");
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", totalIncome);
    }

    private static void askUser() {
        System.out.println("1. Show the seats\n");
        System.out.println("2. Buy a ticket\n");
        System.out.println("3. Statistics\n");
        System.out.println("0. Exit\n");
    }

    public static int[][] createCinemaRoom(int rows, int seats) {
        int[][] cinemaMatrix = new int[rows + 1][seats + 1];
        for(int[] row: cinemaMatrix){
            Arrays.fill(row, 'S');
        }
        cinemaMatrix[0][0] = ' ';
        for(int i = 1; i <= seats; i++) cinemaMatrix[0][i] = i;
        for(int j = 1; j <= rows; j++) cinemaMatrix[j][0] = j;
        return cinemaMatrix;
    }
    public static int[] getCinemaRoom(Scanner scanner) {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        System.out.println();
        return new int[] {rows, seats};
    }
    public static void printCinemaRoom(int[][] cinemaMatrix) {
        System.out.println("Cinema: ");
        for(int i = 0; i < cinemaMatrix.length; i++){
            for(int j = 0; j < cinemaMatrix[0].length; j++){
                if ((i == 0 && j != 0) || (j == 0 && i != 0))
                    try {
                        System.out.print(cinemaMatrix[i][j] + " ");
                        }
                    catch (Exception e){

                    }
                else
                    System.out.print((char)cinemaMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int buyTicket(int[][] cinemaMatrix, Scanner scanner) {
        int price = 0;
        System.out.print("Enter a row number:");
        int clientRow = scanner.nextInt();
        System.out.print("Enter a seat number in that row:");
        int clientSeatNumber = scanner.nextInt();
        System.out.println();
        boolean ok = false;
        boolean moreThan60 = (cinemaMatrix.length - 1) * (cinemaMatrix[0].length - 1) < 60 ? false: true;
        while(!ok) {
            try {
                if (cinemaMatrix[clientRow][clientSeatNumber] == 'S') {
                    if (clientRow >= (int) Math.ceil((cinemaMatrix.length - 1) / 2.0) && moreThan60) {
                        System.out.println("Ticket price: $8");
                        price = 8;
                        ok = true;
                    } else {
                        System.out.println("Ticket price: $10");
                        ok = true;
                        price = 10;
                    }
                } else {
                    System.out.println("That ticket has already been purchased!");
                    System.out.println();
                    System.out.print("Enter a row number:");
                    clientRow = scanner.nextInt();
                    System.out.print("Enter a seat number in that row:");
                    clientSeatNumber = scanner.nextInt();
                }
            }
            catch (Exception e) {
                System.out.println("Wrong input!");
                System.out.println();
                System.out.print("Enter a row number:");
                clientRow = scanner.nextInt();
                System.out.print("Enter a seat number in that row:");
                clientSeatNumber = scanner.nextInt();
            }
        }
        System.out.println();
        cinemaMatrix[clientRow][clientSeatNumber] = 'B';
        return price;
    }
    public static int getPossibleIncome(int[][] cinemaMatrix){
        if((cinemaMatrix[0].length - 1) * (cinemaMatrix.length - 1) < 60)
            return 10 * (cinemaMatrix.length - 1) * (cinemaMatrix[0].length - 1);
        return 10 * (int) Math.floor((cinemaMatrix.length - 1) / 2.0) * (cinemaMatrix[0].length - 1) + 8 * (int) Math.ceil((cinemaMatrix.length - 1) / 2.0) * (cinemaMatrix[0].length - 1);
    }
}