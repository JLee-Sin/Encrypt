import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

//The cypher in charge of converting messages
public class Cypher
{
    private static HashMap<String, String> numsToLetters;
    private static HashMap<String, String> lettersToNums;

    //Constructs a cypher for encrypting and decrypting
    public Cypher() {
        for (int charNum = 0, indexNum = 0; charNum <= 127; ++charNum, ++indexNum) {
            Cypher.numsToLetters.put("" + indexNum, Character.toString((char)charNum));
            Cypher.lettersToNums.put(Character.toString((char)charNum), "" + indexNum);
        }
    }

    //Encrypts a message
    public static String encrypt(final String message) {
        String tempMessage = "";
        final char[] charArray2;
        final char[] charArray = charArray2 = message.toCharArray();
        for (final char c : charArray2) {
            final String s = Character.toString(c);
            if (s.equals(" ")) {
                tempMessage += "A";
            }
            else {
                tempMessage = tempMessage + Cypher.lettersToNums.get(Character.toString(c)) + " ";
            }
        }
        return tempMessage;
    }

    //Decrypts a message
    public static String decrypt(String message) {
        message = message.replaceAll("A", " ");
        System.out.println(message);
        final String[] arrayMessage = message.split(" ", 0);
        final String tempMessage = "";
        final List<String> list = new ArrayList<String>();
        for (int x = 0; x < arrayMessage.length; ++x) {
            list.add(Cypher.numsToLetters.get(arrayMessage[x]));
        }
        System.out.println(list);
        for (int y = 0; y < list.size(); ++y) {
            if (list.get(y) == null) {
                list.set(y, " ");
            }
        }
        String newMess = "";
        for (final String s : list) {
            newMess += s;
        }
        return newMess;
    }

    //A helper method for decrypting
    public static HashMap<String, String> generateNumsToLetters() {
        final HashMap<String, String> numsToLetters = new HashMap<String, String>();
        for (int charNum = 0, indexNum = 0; charNum <= 127; ++charNum, ++indexNum) {
            numsToLetters.put("" + indexNum, Character.toString((char)charNum));
        }
        return numsToLetters;
    }

    //A helper method for encrypting
    public static HashMap<String, String> generateLetterstoNums() {
        final HashMap<String, String> lettersToNums = new HashMap<String, String>();
        for (int charNum = 0, indexNum = 0; charNum <= 127; ++charNum, ++indexNum) {
            lettersToNums.put(Character.toString((char)charNum), "" + indexNum);
        }
        return lettersToNums;
    }

    //A method that relays information about a message after it is converted
    public static String info(final String message) {
        final int rows = (int)Math.sqrt(Math.sqrt(message.length()));
        return "Rows: " + rows + " Cols: " + rows + " Total Dimensions: " + rows * rows;
    }

    static {
        Cypher.numsToLetters = generateNumsToLetters();
        Cypher.lettersToNums = generateLetterstoNums();
    }
}