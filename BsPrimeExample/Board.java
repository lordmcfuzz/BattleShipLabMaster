public class Board {
    /*****************************************
     * CODE that Genearl students get 
     * fully filled out
     ********************************************/
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
    
    /*****************************************
     * END OF CODE that Genearl students get 
     * fully filled out
     ********************************************/
     

     // checks if the ALPHA array contains a letter
    private boolean containsLetter(char letter) {
        for(char let : ALPHA) {
            if (let == letter) {
                return true;
            }
        }
        return false;
    }
    // checks if the NUMBER array contains a num
    private boolean containsNumber(char num) {
        for(char n : NUMBER) {
            if (n == num) {
                return true;
            }
        }
        return false;
    }
    
      /* Changes a character to one of the index choices stored in the final char[] SYMBOL
    *  0 = '\u00B7' Which is a dot
    *  1 = 'X' aka a Hit
    *  2 = '0' aka a Miss
    *  3 = '\u25AA' Which is a box, represents a ship
    */
    public void markBoard(char letter, char num, int index) throws Exception {
        // First make sure that letter and num are valid, then change the state
        if (validChoice(letter, num) && index >= 0 && index < SYMBOL.length) {
            state[letterIndexOf(letter)][numberIndexOf(num)] = SYMBOL[index];
        } else {
            // not found
            throw new Exception("Invalid choices " + letter + num + " with SYMBOL index=" + index);
        }
    }

     
    /* Calculate if it is a Miss or a Hit
    * if a something other than a dot is there return that SYMBOL index
    * else check the ship board if the ship SYMBOL is present
    * returns index for Hit or Miss
    */
    public int calculateHitOrMiss(char letter, char num, Board shipBoard) throws Exception{
        if (validChoice(letter, num)) {
            // check if our board has already been touched, aka a HIT or a MISS has occured, other wise perform the check
            if (getValue(letter, num) == SYMBOL[0]) {
                // middle dot found, Check against the ship board and return a hit or miss
                if (shipBoard.checkShip(letter, num)){
                    // HIT
                    return 1;
                } else {
                    // MISS
                    return 2;
                }
                
            } else {
                // A miss or hit has occured already, return the index of the SYMBOL found
                return symbolIndexOf(getValue(letter, num));
            }
        } else {
            throw new Exception("Invalid choices " + letter + num);
        }
    }
    
    
    // return if a ship is present at the location
    private boolean checkShip(char letter, char num) {
        return getValue(letter, num) == SYMBOL[3];
    }
    
    // returns the character at a given letter/num position
    private char getValue (char letter, char num) {
        return state[letterIndexOf(letter)][numberIndexOf(num)];
    }
    
    /* returns true if ship has been placed, false if there was an issue
    * If direction is true, the ship will be placed from the position(letter,num) downwards
    * If direction is fals,  the ship will be placed from the position(letter,num) to the right
    */
    public boolean placeShip (int length, char letter, char num, boolean direction) throws Exception{
        if (!validChoice(letter, num)) {
            throw new Exception("Invalid choices " + letter + num);
        }
        if (direction){
            // Ship will go vertical, so make sure you have enough letters to place
            // Index of g is 6, If the length is 2, then it could fit as it takes up g and h,
            if (letterIndexOf(letter) + length <= SIZE) {
                // ship fits, now check if it will over lap with another ship
                for(int i = 0; i < length; i++) {
                    if(checkShip(ALPHA[letterIndexOf(letter) + i], num)){
                        //Ship peice found, can't place it here
                        return false;
                    }
                }
                // check passed, place it
                for(int i = 0; i < length; i++) {
                    markBoard(ALPHA[letterIndexOf(letter) + i], num, 3);
                }
                return true;
            } else {
                // ship does not fit
                return false;
            }
        } else {
            // Ship will go horizontal, so make sure you have enough numbers to place
            // Index of 7 is 6, If the length is 2, then it could fit as it takes up 7 and 8,
             if (numberIndexOf(num) + length <= SIZE) {
                // ship fits, now check if it will over lap with another ship
                for(int i = 0; i < length; i++) {
                    if(checkShip(letter, NUMBER[numberIndexOf(num) + i])){
                        //Ship peice found, can't place it here
                        return false;
                    }
                }
                // check passed, place it
                for(int i = 0; i < length; i++) {
                    markBoard(letter, NUMBER[numberIndexOf(num) + i], 3);
                }
                return true;
            } else {
                // ship does not fit
                return false;
            }
        }
    }
    
    // This checks if the attacks match up with the opponent ships, return false on first un attacked ship peice
    public boolean gameOver(Board ships) {
        // So I don't have to deal with silly letters and numbers here, I willjust grab the attack state to make comparison better
        char [][] shipsState = ships.getState();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // if there is a ship there, check state to see if there is a hit
                if(shipsState[row][col] == SYMBOL[3]) {
                    // ship section found, check state for hit, if not return false
                    if(!(state[row][col] == SYMBOL[1])){
                        return false;
                    } else {
                        // It was a hit, just continue on.
                    }
                }
            }
        }
        // Every ship peice was HIT
        return true;
    }
    
    
    // Default Constructor
    public Board() {
        // Create a state of SIZExSIZE
        state = new char[SIZE][SIZE];
        // Init the spots to 
         for(int row = 0; row < SIZE; row++) {
            for(int col = 0; col < SIZE; col ++) {
                // Unicode for the middle dot is U+00B7
                state[row][col] = SYMBOL[0];
            }
        }
    }
    
    
    
}
