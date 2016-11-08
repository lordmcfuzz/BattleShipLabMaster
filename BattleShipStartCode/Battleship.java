import java.util.Scanner;

public class Battleship {
    public static void main (String[] args){
        Board playerOne = new Board();
        playerOne.printBoard();
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
    
       
    
}
