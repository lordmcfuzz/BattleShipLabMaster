import java.util.Scanner;

public class battleship {
    public static void main (String[] args){
        // Create player one Boards
        Board playerOneShips = new Board();
        Board playerOneAttacks = new Board();
        
        // Create player two Boards
        Board playerTwoShips = new Board();
        Board playerTwoAttacks = new Board();
        
        setGameUp(playerOneShips, playerTwoShips);
        String winner = playGame(playerOneShips, playerOneAttacks, playerTwoShips, playerTwoAttacks);
        System.out.println(winner + " won the game");
    }
    
    // crude way to clear the screen
    public final static void clearConsole(){
        for(int i = 0; i<50; i++) System.out.println();
    }
    
    public static void createLine() {
        for(int i =0; i<18; i ++) {
            System.out.print("-");
        }
        System.out.println();
    }
    
    // returns the winner
    public static String playGame(Board playerOneShips, Board playerOneAttacks, Board playerTwoShips, Board playerTwoAttacks) {
        String winner = "NoOne";
        Scanner scan = new Scanner(System.in);
        boolean done = false;
        char letter;
        char num;
        int attack;
        boolean valid = false;
        
        do {
            // Player 1 turn
            clearConsole();
            do {
                System.out.println("Player One's turn, Attack!");
                playerOneAttacks.printBoard();
                System.out.print("Letter: ");
                letter = scan.next().charAt(0);
                System.out.print("Number: ");
                num = scan.next().charAt(0);
                valid = true;
                try {
                    attack = playerOneAttacks.calculateHitOrMiss(letter, num, playerTwoShips);
                    if(attack == 1) {
                        System.out.println("HIT!");
                        playerOneAttacks.markBoard(letter, num, attack);
                    } else if (attack == 2) {
                        System.out.println("MISS!");
                        playerOneAttacks.markBoard(letter, num, attack);
                    } else {
                        throw new Exception("Something went wrong with the attack!");
                    }
                } catch (Exception e) {
                    System.out.println("Not a valid attack" + e.toString());
                    valid = false;
                }
            } while (!valid);
            // Check if won
            done = playerOneAttacks.gameOver(playerTwoShips);
            if (done) {
                winner = "Player 1";
            }
            
            // Player 2 turn if the game is not over
            if(!done) {
                // Player 2 turn
                clearConsole();
                do {
                    System.out.println("Player Two's turn, Attack!");
                    playerTwoAttacks.printBoard();
                    System.out.print("Letter: ");
                    letter = scan.next().charAt(0);
                    System.out.print("Number: ");
                    num = scan.next().charAt(0);
                    valid = true;
                    try {
                        attack = playerTwoAttacks.calculateHitOrMiss(letter, num, playerOneShips);
                        if(attack == 1) {
                            System.out.println("HIT!");
                            playerTwoAttacks.markBoard(letter, num, attack);
                        } else if (attack == 2) {
                            System.out.println("MISS!");
                            playerTwoAttacks.markBoard(letter, num, attack);
                        } else {
                            throw new Exception("Something went wrong with the attack!");
                        }
                    } catch (Exception e) {
                        System.out.println("Not a valid attack" + e.toString());
                        valid = false;
                    }
                } while (!valid);
                // check if won
                done = playerTwoAttacks.gameOver(playerOneShips);
                if (done) {
                    winner = "Player 2";
                }
                }
        } while (!done);
        
        
        return winner;
    }

    
    public static void setGameUp(Board p1, Board p2) {

        
        System.out.println("Player One Set up");
        placeShips(p1);
        
        clearConsole();
        System.out.println("Player Two Set up");
        placeShips(p2);
    }
    
    /* Logic to place all the ships
    *    #	Class of ship	Size
    *    1	Battleship	    4
    *    2	Cruiser	        3
    *    3	Submarine	    3
    *    4	Destroyer   	2
    *    5  Carrrier        5
    */
    private static void placeShips(Board board){
        placeShip(board, "Carrier", 5);
        placeShip(board, "Battleship", 4);
        placeShip(board, "Cruiser", 3);
        placeShip(board, "Submarine", 3);
        placeShip(board, "Destroyer", 2);
        board.printBoard();
    }
    
    private static void placeShip(Board board, String ship, int size ) {
        Scanner scan = new Scanner(System.in);
        boolean success = true;
        do {
            board.printBoard();
            System.out.println("Please Place your " + ship +" of length " + size + ", Name your starting point");
            System.out.print("Letter: ");
            char letter = scan.next().charAt(0);
            System.out.print("Number: ");
            char num = scan.next().charAt(0);
            System.out.print("Direction, 1 is for Vertical, 2 is for Horizonal: ");
            int direction = scan.nextInt();
            try{
                success = board.placeShip(size, letter, num, direction == 1);
            } catch (Exception e) {
                System.out.println(e.toString());
                success = false;
            }
            if (!success) {
                System.out.println("Something went wrong, try again");
            }
        }while(!success);
    }
    
    
    
    
}
