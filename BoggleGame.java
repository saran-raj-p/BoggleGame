import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BoggleGame implements BoggleGameInterface{

    /**
 * Generates a Boggle board of the specified size.
 *
 * @param size The size of the Boggle board. Must be greater than 0.
 * @return A 2D array representing the Boggle board, filled with randomly generated characters.
 *         Returns null if the input size is less than or equal to 0.
 */
@Override
public char[][] generateBoggleBoard(int size) {
    // Check if the input size is valid
    if (size <= 0) {
        return null;
    }

    // Calculate the total number of characters needed for the Boggle board
    int stringLength = size * size;

    // Check if the calculated string length is valid
    if (stringLength <= 0) {
        return null;
    }

    // Generate a random string of characters with the specified length
    String s = generateRandomString(stringLength);

    // Create a 2D array to represent the Boggle board
    char[][] board = new char[size][size];

    // Populate the Boggle board with characters from the generated string
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            board[i][j] = s.charAt(i * size + j);
        }
    }

    // Return the generated Boggle board
    return board;
}

/**
 * Counts the number of valid words on the Boggle board using a given dictionary.
 *
 * @param boggleBoard The 2D array representing the Boggle board. Must not be null.
 * @param dictionary  The dictionary to check against. Must not be null.
 * @return The count of unique valid words found on the Boggle board.
 */
@Override
public int countWords(char[][] boggleBoard, DictInterface dictionary) {
    if (boggleBoard == null || dictionary == null) {
        return 0;
    }

    // Set to store found words and ensure uniqueness
    Set<String> foundWords = new HashSet<>();

    int boardSize = boggleBoard.length;

    // Iterate through each cell in the Boggle board
    for (int i = 0; i < boardSize; i++) {
        for (int j = 0; j < boardSize; j++) {
            // Set to keep track of visited positions during DFS
            Set<Integer> visited = new HashSet<>();
            // StringBuilder to build the current word during DFS
            StringBuilder currentWord = new StringBuilder();
            currentWord.append(Character.toLowerCase(boggleBoard[i][j]));

            // Invoke helper method for DFS
            countWordsHelper(boggleBoard, dictionary, i, j, currentWord, foundWords, visited);
        }
    }

    // Return the count of unique found words
    return foundWords.size();
}

/**
 * Helper method for DFS to find valid words on the Boggle board.
 *
 * @param boggleBoard The 2D array representing the Boggle board.
 * @param dictionary  The dictionary to check against.
 * @param row         The current row index during DFS.
 * @param col         The current column index during DFS.
 * @param currentWord The current word being built during DFS.
 * @param foundWords  The set to store found words.
 * @param visited     The set to keep track of visited positions during DFS.
 */
private void countWordsHelper(char[][] boggleBoard, DictInterface dictionary, int row, int col,
                              StringBuilder currentWord, Set<String> foundWords, Set<Integer> visited) {
    int res = dictionary.searchPrefix(currentWord);

    if ((res == 2 || res == 3) && currentWord.length() >= 3) {
        // Found a valid word, add it to the set
        String word = currentWord.toString().toLowerCase();
        if (!foundWords.contains(word)) {
            foundWords.add(word);
        }
    }

    if (res == 1 || res == 3) {
        int boardSize = boggleBoard.length;
        int position = row * boardSize + col;

        // Explore neighboring positions in DFS
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                int newPosition = newRow * boardSize + newCol;

                // Check if the neighboring position is valid and not visited
                if ((i != 0 || j != 0) && isValid(newRow, newCol, boardSize) && !visited.contains(newPosition)) {
                    char nextChar = Character.toLowerCase(boggleBoard[newRow][newCol]);

                    // Mark the current position as visited
                    visited.add(position);

                    // Recursively explore neighbors
                    currentWord.append(nextChar);
                    countWordsHelper(boggleBoard, dictionary, newRow, newCol, currentWord, foundWords, visited);
                    currentWord.deleteCharAt(currentWord.length() - 1);

                    // Backtrack: Mark the current position as not visited
                    visited.remove(position);
                }
            }
        }
    }
}

/**
 * Checks if a position on the Boggle board is valid.
 *
 * @param row       The row index.
 * @param col       The column index.
 * @param boardSize The size of the Boggle board.
 * @return True if the position is valid, false otherwise.
 */
private boolean isValid(int row, int col, int boardSize) {
    return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
}


/**
 * Counts the number of unique valid words on the Boggle board with a specified length
 * using a given dictionary.
 *
 * @param boggleBoard The 2D array representing the Boggle board. Must not be null.
 * @param dictionary  The dictionary to check against. Must not be null.
 * @param length      The specified length of words to count.
 * @return The count of unique valid words found on the Boggle board with the specified length.
 */
public int countWordsOfCertainLength(char[][] boggleBoard, DictInterface dictionary, int length) {
    if (boggleBoard == null || dictionary == null || length <= 0) {
        return 0;
    }

    // Set to store found words and ensure uniqueness
    Set<String> foundWords = new HashSet<>();
    int boardSize = boggleBoard.length;

    // Iterate through each cell in the Boggle board
    for (int i = 0; i < boardSize; i++) {
        for (int j = 0; j < boardSize; j++) {
            // Set to keep track of visited positions during DFS
            Set<Integer> visitedPositions = new HashSet<>();
            // StringBuilder to build the current word during DFS
            StringBuilder currentWord = new StringBuilder();
            currentWord.append(Character.toLowerCase(boggleBoard[i][j]));

            // Invoke helper method for DFS
            countWordsOfCertainLengthHelper(boggleBoard, dictionary, i, j, currentWord, length, foundWords, visitedPositions);
        }
    }

    // Return the count of unique found words
    return foundWords.size();
}

/**
 * Helper method for DFS to find valid words on the Boggle board with a specified length.
 *
 * @param boggleBoard        The 2D array representing the Boggle board.
 * @param dictionary         The dictionary to check against.
 * @param row                The current row index during DFS.
 * @param col                The current column index during DFS.
 * @param currentWord        The current word being built during DFS.
 * @param targetLength       The specified length of words to count.
 * @param foundWords         The set to store found words.
 * @param visitedPositions   The set to keep track of visited positions during DFS.
 */
private void countWordsOfCertainLengthHelper(char[][] boggleBoard, DictInterface dictionary, int row, int col,
                                             StringBuilder currentWord, int targetLength, Set<String> foundWords,
                                             Set<Integer> visitedPositions) {
    int res = dictionary.searchPrefix(currentWord);

    if (currentWord.length() == targetLength && (res == 2 || res == 3)) {
        // Found a valid word of the specified length
        String word = currentWord.toString();
        if (!foundWords.contains(word)) {
            foundWords.add(word);
            // System.out.println(word);
        }
    }

    if (currentWord.length() < targetLength && (res == 1 || res == 3)) {
        int boardSize = boggleBoard.length;
        int position = row * boardSize + col;

        // Mark the current position as visited
        visitedPositions.add(position);

        // Explore neighboring positions in DFS
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                int newPosition = newRow * boardSize + newCol;

                // Check if the neighboring position is valid and not visited
                if (isValid(newRow, newCol, boggleBoard) && !visitedPositions.contains(newPosition)) {
                    char nextChar = Character.toLowerCase(boggleBoard[newRow][newCol]);

                    // Recursively explore neighbors and update count
                    currentWord.append(nextChar);
                    countWordsOfCertainLengthHelper(boggleBoard, dictionary, newRow, newCol, currentWord, targetLength, foundWords, visitedPositions);
                    currentWord.deleteCharAt(currentWord.length() - 1);
                }
            }
        }

        // Backtrack: Mark the current position as not visited
        visitedPositions.remove(position);
    }
}

/**
 * Checks if a position on the Boggle board is valid.
 *
 * @param row          The row index.
 * @param col          The column index.
 * @param boggleBoard  The 2D array representing the Boggle board.
 * @return True if the position is valid, false otherwise.
 */
private boolean isValid(int row, int col, char[][] boggleBoard) {
    return row >= 0 && row < boggleBoard.length && col >= 0 && col < boggleBoard[row].length;
}



    /**
 * Checks if a given word exists in the provided dictionary.
 *
 * @param dictionary The dictionary to check against. Must not be null.
 * @param word       The word to check for existence in the dictionary.
 * @return True if the word is both a valid word and a prefix to other words in the dictionary, false otherwise.
 */
public boolean isWordInDictionary(DictInterface dictionary, String word) {
    // Use StringBuilder to efficiently perform searchPrefix
    StringBuilder wordBuilder = new StringBuilder(word);

    // Call searchPrefix method to check if the word exists in the dictionary
    int status = dictionary.searchPrefix(wordBuilder);

    // Return true if the word is both a valid word and a prefix to other words
    return (status == 2 || status == 3);
}

    

    /**
 * Checks if a given word exists on the Boggle board.
 *
 * @param boggleBoard The 2D array representing the Boggle board.
 *                    Must not be null.
 * @param word        The word to check for existence on the Boggle board.
 * @return True if the word exists on the Boggle board, false otherwise.
 */
public boolean isWordInBoard(char[][] boggleBoard, String word) {
    int rowCount = boggleBoard.length;
    int colCount = boggleBoard[0].length;

    // Iterate over each cell in the board to start DFS
    for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < colCount; j++) {
            // Create a new boolean array to track visited cells for each DFS search
            boolean[][] visited = new boolean[rowCount][colCount];

            // If the word is found starting from the current cell, return true
            if (isWordInBoardDFS(boggleBoard, i, j, word.toUpperCase(), 0, visited)) {
                return true;
            }
        }
    }

    // Return false if the word is not found on the Boggle board
    return false;
}


/**
 * Private helper method for isWordInBoard.
 * Performs DFS (Depth-First Search) to check if the given word can be formed
 * starting from a specific cell on the Boggle board.
 *
 * @param board   The 2D array representing the Boggle board.
 *                Must not be null.
 * @param row     The current row index.
 * @param col     The current column index.
 * @param word    The word to check for existence on the Boggle board.
 * @param index   The current index in the word being checked.
 * @param visited A boolean array indicating visited cells.
 * @return True if the word can be formed starting from the current cell, false otherwise.
 */
private boolean isWordInBoardDFS(char[][] board, int row, int col, String word, int index, boolean[][] visited) {
    int rowCount = board.length;
    int colCount = board[0].length;

    // Check if the current position is within bounds and not visited
    if (row < 0 || col < 0 || row >= rowCount || col >= colCount || visited[row][col]) {
        return false;
    }

    // Check if the current character in the board matches the current character in the word
    if (board[row][col] != word.charAt(index)) {
        return false;
    }

    // Check if the entire word has been found
    if (index == word.length() - 1) {
        return true;
    }

    // Mark the current position as visited
    visited[row][col] = true;

    // Explore adjacent positions
    if (isWordInBoardDFS(board, row - 1, col, word, index + 1, visited) ||
        isWordInBoardDFS(board, row + 1, col, word, index + 1, visited) ||
        isWordInBoardDFS(board, row, col - 1, word, index + 1, visited) ||
        isWordInBoardDFS(board, row, col + 1, word, index + 1, visited) ||
        isWordInBoardDFS(board, row - 1, col - 1, word, index + 1, visited) ||
        isWordInBoardDFS(board, row - 1, col + 1, word, index + 1, visited) ||
        isWordInBoardDFS(board, row + 1, col - 1, word, index + 1, visited) ||
        isWordInBoardDFS(board, row + 1, col + 1, word, index + 1, visited)) {
        return true;
    }

    // Backtrack: Mark the current position as not visited for other paths
    visited[row][col] = false;

    return false;
}


    /**
 * Finds and returns any valid word on the Boggle board according to the given dictionary.
 *
 * @param boggleBoard The 2D array representing the Boggle board.
 *                    Must not be null.
 * @param dictionary  The dictionary to check against. Must not be null.
 * @return A valid word found on the Boggle board, or null if no valid word is found.
 */
@Override
public String anyWord(char[][] boggleBoard, DictInterface dictionary) {
    int rowCount = boggleBoard.length;
    int colCount = boggleBoard[0].length;

    // Iterate over each cell in the board to start DFS
    for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < colCount; j++) {
            // Create a new boolean array to track visited cells for each DFS search
            boolean[][] visited = new boolean[rowCount][colCount];

            // Call the helper method for each cell
            String word = anyWordDFS(boggleBoard, i, j, "", dictionary, visited);

            // If a valid word is found, return it
            if (word != null) {
                return word;
            }
        }
    }

    // Return null if no valid word is found on the Boggle board
    return null;
}

/**
 * Private helper method for anyWord.
 * Performs DFS (Depth-First Search) to find any valid word on the Boggle board
 * starting from a specific cell.
 *
 * @param board       The 2D array representing the Boggle board.
 *                    Must not be null.
 * @param row         The current row index.
 * @param col         The current column index.
 * @param currentWord The current word being built during the recursive exploration.
 * @param dictionary  The dictionary to check against. Must not be null.
 * @param visited     A boolean array indicating visited cells.
 * @return A valid word found starting from the current cell, or null if no valid word is found.
 */
private String anyWordDFS(char[][] board, int row, int col, String currentWord,
                        DictInterface dictionary, boolean[][] visited) {
    int rowCount = board.length;
    int colCount = board[0].length;

    // Check if the current position is within bounds and not visited
    if (row < 0 || col < 0 || row >= rowCount || col >= colCount || visited[row][col]) {
        return null;
    }

    // Append the current character to the current word
    currentWord += board[row][col];

    // Check if the current word is a valid word or prefix
    int status = dictionary.searchPrefix(new StringBuilder(currentWord.toLowerCase()));

    // If it's a valid word of length three or more, return the word
    if (status == 2 && currentWord.length() >= 3) {
        return currentWord.toString(); // Convert StringBuilder to String
    }

    // If it's not a prefix, stop searching
    if (status == 0) {
        return null;
    }

    // Mark the current position as visited
    visited[row][col] = true;

    // Explore adjacent positions
    String word = anyWordDFS(board, row - 1, col, currentWord, dictionary, visited);
    if (word == null) word = anyWordDFS(board, row + 1, col, currentWord, dictionary, visited);
    if (word == null) word = anyWordDFS(board, row, col - 1, currentWord, dictionary, visited);
    if (word == null) word = anyWordDFS(board, row, col + 1, currentWord, dictionary, visited);
    if (word == null) word = anyWordDFS(board, row - 1, col - 1, currentWord, dictionary, visited);
    if (word == null) word = anyWordDFS(board, row - 1, col + 1, currentWord, dictionary, visited);
    if (word == null) word = anyWordDFS(board, row + 1, col - 1, currentWord, dictionary, visited);
    if (word == null) word = anyWordDFS(board, row + 1, col + 1, currentWord, dictionary, visited);

    // Mark the current position as not visited for other paths
    visited[row][col] = false;

    return word;
}


        /**
 * Marks the path of a given word on the Boggle board, if the word exists.
 *
 * @param boggleBoard The 2D array representing the Boggle board.
 *                    Must not be null.
 * @param word        The word to mark on the Boggle board.
 * @return An ArrayList of Tile objects representing the path of the word on the Boggle board,
 *         or null if the word does not exist.
 */
@Override
public ArrayList<Tile> markWordInBoard(char[][] boggleBoard, String word) {
    int rowCount = boggleBoard.length;
    int colCount = boggleBoard[0].length;

    // Iterate over each cell in the board to start DFS
    for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < colCount; j++) {
            // Create a new boolean array to track visited cells for each DFS search
            boolean[][] visited = new boolean[rowCount][colCount];
            // Create a new ArrayList to store the path of the word
            ArrayList<Tile> path = new ArrayList<>();
            // If the word is found starting from the current cell, return the path
            if (markWordInBoardDFS(boggleBoard, i, j, word.toUpperCase(), 0, visited, path)) {
                return path;
            }
        }
    }

    // Return null if the word is not found on the Boggle board
    return null;
}

/**
 * Private helper method for markWordInBoard.
 * Performs DFS (Depth-First Search) to mark the path of the given word on the Boggle board
 * starting from a specific cell.
 *
 * @param board       The 2D array representing the Boggle board.
 *                    Must not be null.
 * @param row         The current row index.
 * @param col         The current column index.
 * @param word        The word to mark on the Boggle board.
 * @param index       The current index in the word being checked.
 * @param visited     A boolean array indicating visited cells.
 * @param path        An ArrayList representing the path of the word on the Boggle board.
 * @return True if the word can be marked starting from the current cell, false otherwise.
 */

private boolean markWordInBoardDFS(char[][] board, int row, int col, String word, int index,
                                boolean[][] visited, ArrayList<Tile> path) {
    int rowCount = board.length;
    int colCount = board[0].length;

    // Check if the current position is within bounds and not visited
    if (row < 0 || col < 0 || row >= rowCount || col >= colCount || visited[row][col]) {
        return false;
    }

    // Check if the current character in the board matches the current character in the word
    if (board[row][col] != word.charAt(index)) {
        return false;
    }

    // Check if the entire word has been found
    if (index == word.length() - 1) {
        // Add the current tile to the path
        path.add(new Tile(row, col));
        return true;
    }

    // Mark the current position as visited
    visited[row][col] = true;

    // Add the current tile to the path
    path.add(new Tile(row, col));

    // Explore adjacent positions
    if (markWordInBoardDFS(board, row - 1, col, word, index + 1, visited, path) ||
        markWordInBoardDFS(board, row + 1, col, word, index + 1, visited, path) ||
        markWordInBoardDFS(board, row, col - 1, word, index + 1, visited, path) ||
        markWordInBoardDFS(board, row, col + 1, word, index + 1, visited, path) ||
        markWordInBoardDFS(board, row - 1, col - 1, word, index + 1, visited, path) ||
        markWordInBoardDFS(board, row - 1, col + 1, word, index + 1, visited, path) ||
        markWordInBoardDFS(board, row + 1, col - 1, word, index + 1, visited, path) ||
        markWordInBoardDFS(board, row + 1, col + 1, word, index + 1, visited, path)) {
        return true;
    }

    // Mark the current position as not visited for other paths
    visited[row][col] = false;

    // Remove the last tile from the path as it doesn't lead to a valid solution
    path.remove(path.size() - 1);

    return false;
}

    


    /**
 * Checks if a list of tiles forms a valid path on the Boggle board corresponding to a given word.
 *
 * @param boggleBoard The 2D array representing the Boggle board.
 *                    Must not be null.
 * @param tiles       An ArrayList of Tile objects representing the path to check.
 * @param word        The word associated with the path.
 * @return True if the list of tiles forms a valid path for the given word, false otherwise.
 */
@Override
public boolean checkTiles(char[][] boggleBoard, ArrayList<Tile> tiles, String word) {
    if (tiles == null || tiles.isEmpty() || word.length() < 3) {
        // Empty list, cannot form a valid path or word is less than three letters
        return false;
    }

    int rowCount = boggleBoard.length;
    int colCount = boggleBoard[0].length;

    // Iterate through the list of tiles
    for (int i = 0; i < tiles.size(); i++) {
        Tile currentTile = tiles.get(i);
        
        // Check if the letter on the current tile matches the corresponding letter in the word
        if (i < word.length() && Character.toLowerCase(boggleBoard[currentTile.row][currentTile.col]) !=
        Character.toLowerCase(word.charAt(i))) {
            return false;
        }

        if (i > 0) {
            Tile prevTile = tiles.get(i - 1);

            // Check if the current tile and the previous tile are adjacent
            if (!areTilesAdjacent(currentTile, prevTile, rowCount, colCount)) {
                return false;
            }
        }
    }

    return true;
}

/**
 * Private helper method for checkTiles.
 * Checks if two tiles are adjacent on the Boggle board.
 *
 * @param tile1     The first Tile.
 * @param tile2     The second Tile.
 * @param rowCount  The number of rows on the Boggle board.
 * @param colCount  The number of columns on the Boggle board.
 * @return True if the tiles are adjacent, false otherwise.
 */
private boolean areTilesAdjacent(Tile tile1, Tile tile2, int rowCount, int colCount) {
    // Check if two tiles are adjacent horizontally, vertically, or diagonally
    int rowDiff = Math.abs(tile1.row - tile2.row);
    int colDiff = Math.abs(tile1.col - tile2.col);

    return (rowDiff <= 1 && colDiff <= 1 && !(rowDiff == 0 && colDiff == 0) &&
            tile1.row >= 0 && tile1.row < rowCount && tile1.col >= 0 && tile1.col < colCount &&
            tile2.row >= 0 && tile2.row < rowCount && tile2.col >= 0 && tile2.col < colCount);
}


    

/**
 * Finds and returns any valid word on the Boggle board according to the given dictionary,
 * with a specified length.
 *
 * @param boggleBoard The 2D array representing the Boggle board.
 *                    Must not be null.
 * @param dictionary  The dictionary to check against. Must not be null.
 * @param length      The specified length of the word to find.
 * @return A valid word found on the Boggle board with the specified length, or null if no such word is found.
 */
@Override
public String anyWord(char[][] boggleBoard, DictInterface dictionary, int length) {
    int rowCount = boggleBoard.length;
    int colCount = boggleBoard[0].length;
    length = length -1;
    // Iterate over each cell in the board to start DFS
    for (int i = 0; i < rowCount; i++) {
        for (int j = 0; j < colCount; j++) {
            // Create a new boolean array to track visited cells for each DFS search
            boolean[][] visited = new boolean[rowCount][colCount];
            // Create a StringBuilder to build the current word during DFS
            StringBuilder currentWord = new StringBuilder();
            // Find and return any valid word with the specified length starting from the current cell
            String word = anyWordDFS(boggleBoard, i, j, currentWord, dictionary, visited, length);
            if (word != null) {
                return word;
            }
        }
    }

    // Return null if no valid word of the specified length is found on the Boggle board
    return null;
}

/**
 * Private helper method for anyWord.
 * Performs DFS (Depth-First Search) to find any valid word on the Boggle board
 * with a specified length starting from a specific cell.
 *
 * @param board       The 2D array representing the Boggle board.
 *                    Must not be null.
 * @param row         The current row index.
 * @param col         The current column index.
 * @param currentWord The current word being built during the recursive exploration.
 * @param dictionary  The dictionary to check against. Must not be null.
 * @param visited     A boolean array indicating visited cells.
 * @param length      The specified length of the word to find.
 * @return A valid word found on the Boggle board with the specified length starting from the current cell,
 *         or null if no such word is found.
 */
private String anyWordDFS(char[][] board, int row, int col, StringBuilder currentWord,
                        DictInterface dictionary, boolean[][] visited, int length) {
    int rowCount = board.length;
    int colCount = board[0].length;
    // Check if the current position is within bounds and not visited
    if (row < 0 || col < 0 || row >= rowCount || col >= colCount || visited[row][col]) {
        return null;
    }

    // Check the length of the current word once
    int currentWordLength = currentWord.length();

    // Append the current character to the current word
    currentWord.append(Character.toLowerCase(board[row][col]));
    
    // Check if the current word is a valid word or prefix
    int status = dictionary.searchPrefix(currentWord);

    // If it's a valid word of the specified length, return the word
    if (status == 2 && currentWordLength == length) {
        
        return currentWord.toString();
    }

    // If it's not a prefix or the length is not equal to the specified length, stop searching
    if (status == 0 || currentWordLength > length) {
        // Remove the last character from the current word for backtracking
        currentWord.setLength(currentWordLength);
        return null;
    }

    // Mark the current position as visited
    visited[row][col] = true;

    // Explore adjacent positions
    String word = anyWordDFS(board, row - 1, col, currentWord, dictionary, visited, length);
    if (word == null) word = anyWordDFS(board, row + 1, col, currentWord, dictionary, visited, length);
    if (word == null) word = anyWordDFS(board, row, col - 1, currentWord, dictionary, visited, length);
    if (word == null) word = anyWordDFS(board, row, col + 1, currentWord, dictionary, visited, length);
    if (word == null) word = anyWordDFS(board, row - 1, col - 1, currentWord, dictionary, visited, length);
    if (word == null) word = anyWordDFS(board, row - 1, col + 1, currentWord, dictionary, visited, length);
    if (word == null) word = anyWordDFS(board, row + 1, col - 1, currentWord, dictionary, visited, length);
    if (word == null) word = anyWordDFS(board, row + 1, col + 1, currentWord, dictionary, visited, length);

    // Mark the current position as not visited for other paths
    visited[row][col] = false;

    // Remove the last character from the current word for backtracking
    currentWord.setLength(currentWordLength);

    return word;
}


    /**
 * Generates a random string of the specified length consisting of uppercase letters.
 *
 * @param length The desired length of the random string.
 * @return A randomly generated string of uppercase letters.
 */

    private String generateRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString().toUpperCase();

        // System.out.println(generatedString);
        return generatedString;
    }
    
}
