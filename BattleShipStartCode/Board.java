public class Board {

    // Two-dimentional array that keeps track of the board state
    private char[][] state;
    
    // Class Constants
    private final int SIZE = 8; // 8 by 8 board. If this changes, aplha and number must change too
    private final char[] ALPHA = "abcdefgh".toCharArray(); // Must be the same length as SIZE
    private final char[] NUMBER = "12345678".toCharArray(); // Must be the same length as SIZE
    private final char[] SYMBOL = {'\u00B7','X','0','\u25B4'}; // dot, HIT, MISS, Ship SYMBOL, If you wish to pick different SYMBOLs, be sure to use SMALL SYMBOLs
    
    
    
    // Getter and Setter for the board state
    public char[][] getState() { return state;}
    public void setState(char[][] newState) { state = newState; }
    
    /**
     * Prints the board in the following format
     *        1 2 3 4 5 6 7 8
     *      a · · · · · · · ·
     *      b · · · · · · · ·
     *      c · · · · · · · ·
     *      d · · · · · · · ·
     *      e · · · · · · · ·
     *      f · · · · · · · ·
     *      g · · · · · · · ·
     *      h · · · · · · · ·
     */
    public void printBoard() {
        //Print out the letters
        System.out.print("  ");
        for(char num : NUMBER) {
            System.out.print(num + " ");
        }
        System.out.println();
        // Print out the rest of the board
        for(int row = 0; row < SIZE; row++) {
            System.out.print(ALPHA[row] + " ");
            for(int col = 0; col < SIZE; col ++) {
                System.out.print(state[row][col] + " ");
            }
            System.out.println();
        }
    }
    
    // returns the index of a particular letter or -1 if not found
    private int letterIndexOf(char letter) {
        for (int i =0; i< SIZE; i++) {
            if (letter == ALPHA[i]){
                return i;
            }
        }
        return -1;
    }
    
    // returns the index of a particular number or -1 if not found
    private int numberIndexOf(char num) {
        for (int i =0; i < SIZE; i++) {
            if (num ==  NUMBER[i]){
                return i;
            }
        }
        return -1;
    }
    
    // returns the index of a particular symbol or -1 if not found
    private int symbolIndexOf(char sym) {
        for (int i =0; i < SIZE; i++) {
            if (sym ==  SYMBOL[i]){
                return i;
            }
        }
        return -1;
    }

    
    // return true if the choice is valid
    private boolean validChoice (char letter, char num) {
        return containsLetter(letter) && containsNumber(num);
    }
    

    
}
