import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * WordGenerator Class
 */
public class WordGenerator {

    /**
     * Private Class fields
     */
    private int word_count;
    private int sentence_count;
    private static ArrayList<String> wordList = new ArrayList<>();
    private int text_word_count=0;
    private int text_sentence_count=0;

    /**
     * WordGenerator class constructor
     * 
     * @param filename
     */
    public WordGenerator(String filename) throws FileNotFoundException {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            readWords(fileScanner);
            this.text_word_count=countTextWords();
            this.text_sentence_count=countTextSenetences();

        } catch (FileNotFoundException fnf) {
            System.out.println("File Not Found");
            System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
            System.exit(-1);
        } catch (Exception ex) {
            System.out.println("Error in opening the file");
            System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
            System.exit(-1);
        }
    }

    /**
     * Reads the words from the scannner object and initializes the ArrayList in the
     * constructor
     * 
     * @param fileScanner
     */
    public static void readWords(Scanner fileScanner) {
        try {
            while (fileScanner.hasNext()) {
                wordList.add(fileScanner.next());
            }
        } catch (Exception ex) {
            System.out.println("Error in reading the file");
            System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
            System.exit(-1);
        }
    }

    /**
     * Checks if the word is a sentence delimiting word, i.e if the word is such that it 
     * occurs at the end of a sentence.
     * @param word
     * @return boolean
     */
    public static boolean checkEndOfSentence(String word) {
        String lastChar = word.substring(word.length() - 1, word.length());
        if (lastChar.equals("!")
                || lastChar.equals("?")
                || lastChar.equals(".")) {
            return true;
        }
        return false;
    }

    /**
     * Returns the next string in the ArrayList that has been fed words from the
     * File Scanner Object
     * 
     * @return String
     */
    public String next() {
        if (word_count != wordList.size()) {
            String nextWord = wordList.get(word_count);
            word_count++;
            if (checkEndOfSentence(nextWord)) {
                sentence_count++;
            }
            return nextWord;
        } else if (word_count == text_word_count) {
            sentence_count++;
            return wordList.get(word_count);
        } else {
            return null;
        }
    }

    /**
     * Returns true if the End of File (EOF) is reached, i.e word_count==text_word_count
     * @return boolean
     */
    public boolean EOF(){
        if(word_count==text_word_count){
            return true;
        } 
        return false;
    }

    /**
     * Counts and returns the total number of words in the text 
     * @return int
     */
    public int countTextWords(){
        return wordList.size();
    }

    /**
     * Returns the total number of sentences in the text
     * @return int
     */
    public int countTextSenetences(){
        int num_sentences=0;
        for(int idx=0;idx<wordList.size();idx++){
            if (checkEndOfSentence(wordList.get(idx))){
                num_sentences++;
            }
        }
        return num_sentences;
    }
    /**
     * Accessor method for text_word_count private field
     * @return int
     */
    public int getTextWordCount(){
        return text_word_count;
    }

    /**
     * Accessor method for sentence_word_count privatefield
     * @return int
     */
    public int getTextSentenceCount(){
        return text_sentence_count;
    }

    /**
     * Accessor method for the field wordCount, stores the 
     * number of words reached SO FAR
     * @return int
     */
    public int getWordCount(){
        return word_count;
    }
    
    /**
     * Accessor method for the field sentenceCount, stores the 
     * number of words reached SO FAR
     * @return int 
     */
    public int getSentenceCount(){
        return sentence_count;
    }
}
