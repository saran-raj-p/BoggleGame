# Boggle Board Generator and Word Checker

## `generateBoggleBoard(int size)`

### Data Structures:
- 2D array `char[][]` representing the Boggle board.

### Algorithm:
1. Validate input size.
2. Generate a random string of uppercase letters using `generateRandomString`.
3. Populate the board with characters from the string.

### Debugging:
- Ensured size validation to avoid negative or zero-sized boards.
- Checked if the generated string matches the expected length.

---

## `countWords(char[][] boggleBoard, DictInterface dictionary)`

### Data Structures:
- 2D array `char[][]` representing the Boggle board.
- Interface `DictInterface` for the dictionary.

### Algorithm:
1. Iterate through each cell on the board.
2. Perform DFS using `countWordsHelper` for each cell.
3. Mark visited cells and explore neighboring cells.
4. Validate formed words against the dictionary using `searchPrefix`.

### Debugging:
- Ensured proper resetting of the board for each DFS call.
- Checked for correct marking of visited cells and backtracking.

---

## `countWordsOfCertainLength(char[][] boggleBoard, DictInterface dictionary, int wordLength)`

### Data Structures:
- 2D array `char[][]` representing the Boggle board.
- Interface `DictInterface` for the dictionary.

### Algorithm:
1. Iterate through each cell on the board.
2. Perform DFS using `countWordsOfCertainLengthHelper` for each cell.
3. Mark visited cells and explore neighboring cells.
4. Validate formed words against the dictionary using `searchPrefix`.
5. Check for the required word length.

### Debugging:
- Ensured proper resetting of the board for each DFS call.
- Checked for correct marking of visited cells and backtracking.
- Validated the word length during the recursive calls.

---

## `isWordInDictionary(DictInterface dictionary, String word)`

### Data Structures:
- Interface `DictInterface` for the dictionary.

### Algorithm:
1. Use a `StringBuilder` to efficiently perform `searchPrefix`.
2. Call `searchPrefix` to check if the word exists in the dictionary.

### Debugging:
- Ensured correct usage of `searchPrefix`.
- Validated the returned status to determine word existence.

---

## `isWordInBoard(char[][] boggleBoard, String word)`

### Data Structures:
- 2D array `char[][]` representing the Boggle board.

### Algorithm:
1. Iterate through each cell on the board.
2. Perform DFS using `isWordInBoardDFS`.
3. Explore neighboring cells and match the characters in the word.

### Debugging:
- Verified the correctness of DFS traversal.
- Checked character matching and proper backtracking.

---

## `anyWord(char[][] boggleBoard, DictInterface dictionary)`

### Data Structures:
- 2D array `char[][]` representing the Boggle board.
- Interface `DictInterface` for the dictionary.

### Algorithm:
1. Iterate through each cell on the board.
2. Perform DFS using `anyWordDFS`.
3. Explore neighboring cells and return the first valid word found.

### Debugging:
- Checked DFS traversal and backtracking.
- Ensured correct handling of the first found word.

---

## `markWordInBoard(char[][] boggleBoard, String word)`

### Data Structures:
- 2D array `char[][]` representing the Boggle board.
- `ArrayList<Tile>` for marking the path.

### Algorithm:
1. Iterate through each cell on the board.
2. Perform DFS using `markWordInBoardDFS`.
3. Explore neighboring cells and mark the path with `Tile` objects.

### Debugging:
- Verified the correctness of DFS traversal.
- Checked character matching and proper backtracking.
- Ensured correct marking of the path.

---

## `checkTiles(char[][] boggleBoard, ArrayList<Tile> tiles, String word)`

### Data Structures:
- 2D array `char[][]` representing the Boggle board.
- `ArrayList<Tile>` for the provided tiles.

### Algorithm:
1. Iterate through the list of tiles.
2. Check adjacency and character matching.
3. Validate the last tile and its corresponding letter.

### Debugging:
- Ensured proper handling of tile adjacency.
- Checked character matching and validation of the last tile.

---

## `anyWord(char[][] boggleBoard, DictInterface dictionary, int length)`

### Data Structures:
- 2D array `char[][]` representing the Boggle board.
- Interface `DictInterface` for the dictionary.

### Algorithm:
1. Iterate through each cell on the board.
2. Perform DFS using `anyWordDFS`.
3. Explore neighboring cells and return the first valid word of the specified length found.

### Debugging:
- Checked DFS traversal, backtracking, and length constraint.
- Ensured correct handling of the first found word.

---

## `generateRandomString(int length)`

### Data Structures:
- `Random` class for generating random numbers.
- `StringBuilder` for constructing the random string.

### Algorithm:
1. Set the left and right limits for uppercase letters.
2. Generate random integers within the limits.
3. Convert the integers to characters and append to the `StringBuilder`.

### Debugging:
- Checked the correctness of the generated random string.
- Ensured proper conversion from integers to characters.

---

## Asymptotic Analysis

### `countWords` and `countWordsHelper`
- **Time Complexity (Worst Case):** O(n^4)
  - n: Board size.
  - Nested loops explore all cells and their neighbors, resulting in a quadratic time complexity.

### `countWordsOfCertainLength`
- **Time Complexity (Worst Case):** O(n^4)
  - Similar to `countWords` with an additional check for word length.

### `isWordInBoard` and `anyWord`
- **Time Complexity (Worst Case):** O(n^4)
  - DFS explores all possible paths on the board.

### `markWordInBoard` and `checkTiles`
- **Time Complexity (Worst Case):** O(n^4)
  - DFS traversal exploring all paths.

### `anyWord` with Length Constraint
- **Time Complexity (Worst Case):** O(n^4)
  - DFS on the board with additional length check.

### `generateRandomString`
- **Time Complexity (Worst Case):** O(length)
  - Linear time complexity based on the specified length.
