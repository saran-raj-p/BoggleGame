import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private BoggleGameInterface boggleGame;
    private Scanner scan;
    private char[][] boggleBoard;
    private DictInterface D;
    private MenuProgram program;

    public static void main(String[] args) {
        new Main();

    }

    public Main() {
        ArrayList<CallableMenuItem> menuItems = new ArrayList<>();
        boggleGame = new BoggleGame();
        scan = new Scanner(System.in);

        menuItems.add(new CreateBoggleMenuItem());
        menuItems.add(new LoadDictionaryMenuItem());
        menuItems.add(new DisplayBoggleMenuItem());
        menuItems.add(new CountWordsMenuItem());
        menuItems.add(new CountWordsWithCertainLengthMenuItem());
        menuItems.add(new CheckWordInDictionaryMenuItem());
        menuItems.add(new CheckWordInBoardMenuItem());
        menuItems.add(new ExitMenuItem());
        program = new MenuProgram(menuItems);
        program.run();

    }

    private class CreateBoggleMenuItem implements CallableMenuItem {

        @Override
        public String getDisplayString() {
            return "Generate a new Boggle board";
        }

        @Override
        public void handle() {
            int size = program.readInteger("Please enter board dimension: ");
            boggleBoard = boggleGame.generateBoggleBoard(size);
        }

    }

    private class DisplayBoggleMenuItem implements CallableMenuItem {

        @Override
        public String getDisplayString() {
            return "Display the Boggle board";
        }

        @Override
        public void handle() {
            if (boggleBoard == null) {
                System.out.println("Please generate a board first.");
            } else {
                for (int i = 0; i < boggleBoard.length; i++) {
                    for (int j = 0; j < boggleBoard.length; j++) {
                        System.out.print(Character.toUpperCase(boggleBoard[i][j]) + " ");
                    }
                    System.out.println();
                }
            }
        }

    }

    private class LoadDictionaryMenuItem implements CallableMenuItem {

        @Override
        public String getDisplayString() {
            return "Load a dictionary from a file";
        }

        @Override
        public void handle() {
            String fileName = null;
            Scanner fileScan = null;
            while (true) {
                fileName = program.readString("Please enter filename: ");
                try {
                    fileScan = new Scanner(new FileInputStream(fileName));
                    break;
                } catch (FileNotFoundException e) {
                    System.out.println("File not found!");
                }
            }
            String st;
            D = new MyDictionary();

            while (fileScan.hasNext()) {
                st = fileScan.nextLine();
                D.add(st);
            }

        }

    }

    private class CountWordsMenuItem implements CallableMenuItem {

        @Override
        public String getDisplayString() {
            return "Get number of possible words";
        }

        @Override
        public void handle() {
            if (boggleBoard == null || D == null) {
                System.out.println("Please generate a board and load a dictionary first!");
            } else {
                int words = boggleGame.countWords(boggleBoard, D);
                System.out.println("There are " + words + " possible words");
            }
        }

    }

    private class CountWordsWithCertainLengthMenuItem implements CallableMenuItem {

        @Override
        public String getDisplayString() {
            return "Get number of possible words with a certain length";
        }

        @Override
        public void handle() {
            if (boggleBoard == null || D == null) {
                System.out.println("Please generate a board and load a dictionary first!");
            } else {
                int length = program.readInteger("Please enter the required word length: ");
                int words = boggleGame.countWordsOfCertainLength(boggleBoard, D, length);
                System.out.println("There are " + words + " possible words of length " + length);
            }
        }

    }

    private class CheckWordInDictionaryMenuItem implements CallableMenuItem {

        @Override
        public String getDisplayString() {
            return "Check if a word is in the dictionary";
        }

        @Override
        public void handle() {
            if (D == null) {
                System.out.println("Please load a dictionary first!");
            } else {
                String word = program.readString("Please enter a word to check: ");
                boolean check = boggleGame.isWordInDictionary(D, word);
                if (check) {
                    System.out.println(word + " exists in the dictionary.");
                } else {
                    System.out.println(word + " doesn't exist in the dictionary.");
                }
            }
        }
    }

    private class CheckWordInBoardMenuItem implements CallableMenuItem {

        @Override
        public String getDisplayString() {
            return "Check if a word is feasible in the board";
        }

        @Override
        public void handle() {
            if (boggleBoard == null){
                System.out.println("Please generate a board first!");
            } else {
                String word = program.readString("Please enter a word to check: ");
                boolean check = boggleGame.isWordInBoard(boggleBoard, word);
                if (check) {
                    System.out.println(word + " exists in the board.");
                } else {
                    System.out.println(word + " doesn't exist in the board.");
                }
            }
        }
    }

    private class ExitMenuItem implements CallableMenuItem {

        @Override
        public String getDisplayString() {
            return "Exit";
        }

        @Override
        public void handle() {
            System.out.println("Good Bye!");
            System.exit(0);
        }

    }

}
