import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.Font;

/**
 * SpeedReader class
 */
public class SpeedReader {

    /**
     * Private class fields
     */
    private String fileName;
    private int width;
    private int height;
    private int font_size;
    private int wpm;

    /**
     * SpeedReader Constructor
     * 
     * @param fileName
     * @param width
     * @param height
     * @param font_size
     * @param wpm
     */
    public SpeedReader(String fileName, int width, int height, int font_size, int wpm) {
        this.fileName = fileName;
        this.width = width;
        this.height = height;
        this.font_size = font_size;
        this.wpm = wpm;
    }

    /**
     * Sets GUI options. Re-evaluates font size if too small dor screen
     * 
     * @param GUI
     * @param font_size
     * @param height
     * @param width
     */
    public void setGUIOptions(Graphics GUI, int font_size, int height, int width) {
        if (font_size >= (height / 3) || font_size >= (width / 3)) {
            if (height >= width)
                font_size = height / 10;
            if (height <= width)
                font_size = width / 10;
            System.out.println(
                    "Detected font size is too big for screen size, program is setting font size to: " + font_size);
        }
        Font title = new Font("Courier", Font.BOLD, (width/20));
        GUI.setFont(title);
        GUI.drawString("SPEED READER",width/3 ,height/10);

        Font font = new Font("Courier", Font.BOLD, font_size);
        GUI.setFont(font);
    }

    /**
     * Starts RSVP based on params provided.
     * 
     * @param GUI
     * @param height
     * @param width
     * @param wordGenerator
     * @throws InterruptedException
     */
    public void startReader(Graphics GUI, int height, int width, int wpm, WordGenerator wordGenerator)
            throws InterruptedException {
        int X_POINT = width / 20;
        int Y_point = height / 2;
        long lag_time = calculateLagTime(wpm);

        
        while (!wordGenerator.EOF()) {
            GUI.setColor(Color.WHITE);
            GUI.drawString(wordGenerator.next(), X_POINT, Y_point);
            Thread.sleep(lag_time);
            GUI.setColor(Color.BLACK);
            GUI.fillRect(0, width / 3, width, height / 3);
        }

        String report = "WPM: " + wpm +
                "\n Total words: " + wordGenerator.getTextWordCount() +
                "\n Total Sentences: " + wordGenerator.getTextSentenceCount();

        GUI.setColor(Color.BLACK);
        Font font = new Font("Courier", Font.BOLD, (width/30));
        GUI.setFont(font);
        GUI.drawString(report, width / 50, height - (height / 10));
    }

    /**
     * For X wpm,
     * X words -> 1 min
     * X words -> 60 s
     * X/60 words -> 1s
     * X/60 words -> 1000 ms
     * 1 word -> 1000/(X/60) ms
     * 1 Word -> 1000*60/X ms
     * Returns the lag time for after each succeessive word, as calculated by
     * 1000*60/wpm
     * 
     * @param wpm
     * @return long
     */
    public static long calculateLagTime(int wpm) {
        return (1000 * 60) / wpm;
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        int hard_width = 500;
        int hard_height = 500;
        int hard_font_size = 50;
        int hard_wpm = 400;
        String hard_fileName="Sample.txt";

        String argFilename="";
        int argWidth=0;
        int argHeight=0;
        int argFontSize=0;
        int argWPM=0;

        /**
         * Parsing filename from CLI
         */
        try{
            argFilename=args[0];
        }
        catch(Exception ex){
            System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
            System.err.println("Invalid File name");
            System.exit(-1);
            
        }

        /**
         * Parsing Width from CLI
         */
        try{
            argWidth=Integer.parseInt(args[1]);
        }
        catch(Exception ex){
            System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
            System.err.println("Invalid width");
            System.exit(-1);
        }


        /**
         * Parsing height from CLI
         */
        try{
            argHeight=Integer.parseInt(args[2]);
        }
        catch(Exception ex){
            System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
            System.err.println("Invalid height");
            System.exit(-1);
        }

        /**
         * Parsing font size from CLI
         */
        try{
            argFontSize=Integer.parseInt(args[3]);
        }
        catch(Exception ex){
            System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
            System.err.println("Invalid font size");
            System.exit(-1);
        }

        /**
         * Parsing WPM from CLI
         */
        try{
            argWPM=Integer.parseInt(args[4]);
        }
        catch(Exception ex){
            System.out.println("Usage: SpeedReader <filename> <width> <height> <font size> <wpm>");
            System.err.println("Invalid WPM");
            System.exit(-1);
        }

        DrawingPanel panel = new DrawingPanel(argWidth,argHeight);
        Graphics speedReaderGUI = panel.getGraphics();
        
        SpeedReader speedReader=new SpeedReader(argFilename,argHeight,argWidth,argFontSize,argWPM);
        speedReader.setGUIOptions(speedReaderGUI, argFontSize, argHeight,argWidth);
        WordGenerator wordGenerator = new WordGenerator(argFilename);
        speedReader.startReader(speedReaderGUI,argHeight, argWidth, argWPM, wordGenerator);
    }
}
