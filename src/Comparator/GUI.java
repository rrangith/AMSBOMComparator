//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Comparator;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;

public class GUI extends JFrame {

    private JLabel instructionsLabel;
    private JLabel fileOneLabel;
    private JLabel fileTwoLabel;
    private JTextField fileOneField;
    private JTextField fileTwoField;
    private JTextField rowField;
    private JButton startButton;
    String fileOneString = "";
    String fileTwoString = "";
    File directory;

    private JPanel panel;
    private JButton helpButton;
    private JTextArea area;

    GUI() {
        super("AMSBOMComparator");
        this.setSize(1000, 275);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, 1));
        this.instructionsLabel = new JLabel("Select Two Files to Compare. Must be .csv ");
        panel.add(this.instructionsLabel);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, 1));
        JPanel fileOnePanel = new JPanel();
        fileOnePanel.setLayout(new FlowLayout());
        this.fileOneLabel = new JLabel("AMS File");
        this.fileOneField = new JTextField(40);
        fileOnePanel.add(this.fileOneLabel);
        fileOnePanel.add(this.fileOneField);
        JButton fileOneBut = new JButton("Select");
        fileOneBut.addActionListener(new GUI.FileOneButListener());
        fileOnePanel.add(fileOneBut);
        JPanel fileTwoPanel = new JPanel();
        fileTwoPanel.setLayout(new FlowLayout());
        this.fileTwoLabel = new JLabel("BOM File");
        this.fileTwoField = new JTextField(40);
        new JFileChooser();
        JButton fileTwoBut = new JButton("Select");
        fileTwoBut.addActionListener(new GUI.FileTwoButListener());
        fileTwoPanel.add(this.fileTwoLabel);
        fileTwoPanel.add(this.fileTwoField);
        fileTwoPanel.add(fileTwoBut);
        inputPanel.add(fileOnePanel);
        inputPanel.add(fileTwoPanel);
        panel.add(inputPanel);
        JPanel startPanel = new JPanel(new FlowLayout());
        this.startButton = new JButton("Start");
        this.startButton.addActionListener(new GUI.StartButtonListener());
        startPanel.add(this.startButton);
        helpButton = new JButton("Help");
        helpButton.addActionListener(new HelpButtonListener());
        startPanel.add(helpButton);
        panel.add(startPanel);

        this.add(panel);
        this.setVisible(true);
    }

    public class FileTwoButListener implements ActionListener {
        public FileTwoButListener() {
        }

        public void actionPerformed(ActionEvent e) {
            FileChooserGUI fcg;
            if(GUI.this.directory != null) {
                fcg = new FileChooserGUI(GUI.this.directory);

            } else {
                fcg = new FileChooserGUI();
            }


            GUI.this.fileTwoString = fcg.getPath();
            GUI.this.fileTwoField.setText(GUI.this.fileTwoString);
            GUI.this.directory = fcg.getDir();
        }
    }

    public class FileOneButListener implements ActionListener {
        public FileOneButListener() {
        }

        public void actionPerformed(ActionEvent e) {
            FileChooserGUI fcg;
            if(GUI.this.directory != null) {
                fcg = new FileChooserGUI(directory);
            } else {
                fcg = new FileChooserGUI();
            }

            GUI.this.fileOneString = fcg.getPath();
            GUI.this.fileOneField.setText(GUI.this.fileOneString);
            GUI.this.directory = fcg.getDir();
        }
    }

    public class HelpButtonListener implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            final String newline = "\n";
            area = new JTextArea(20, 20);
            area.setEditable(false);
            area.append("AMSBOMComparator.jar" + newline);
            area.append("---------------------------------------------------------------------------------------------" + newline);
            area.append("Compares 2 CSV files and outputs differences, missing information, and duplicate information." + newline);
            area.append("Enter the AMS CSV file using the select button."+newline);
            area.append("Enter the BOM CSV file using the select button." + newline);
            area.append("After pressing the Start button, a text file will automatically open, showing all errors." + newline);
            panel.add(area);
            panel.revalidate();
            panel.repaint();
            helpButton.setEnabled(false);
        }
    }

    public class StartButtonListener implements ActionListener {
        String fieldOne;
        String fieldTwo;

        public void actionPerformed(ActionEvent e) {
            this.fieldOne = GUI.this.fileOneField.getText();
            this.fieldTwo = GUI.this.fileTwoField.getText();
            ArrayList<MisMatch> differences = new ArrayList();
            if(this.fieldOne.length() > 5 && this.fieldTwo.length() > 5) {
                if(this.fieldOne.substring(this.fieldOne.length() - 4).equals(".csv") && this.fieldTwo.substring(this.fieldTwo.length() - 4).equals(".csv")) {

                    File fixedBom = new File("FixedBOM.csv");
                    try{

                        FileReader fr = new FileReader(fieldTwo);
                        BufferedReader br = new BufferedReader(fr);
                       // FileOutputStream fs = new FileOutputStream(fixedBom);
                        PrintWriter pw = new PrintWriter(fixedBom);



                        LinkedList<String> designatorList = new LinkedList<String>();

                        String line;
                        while ((line = br.readLine()) != null){ //loops through bom

                            String cvsSplit = ", ";
                            if (line.indexOf(", ") == -1) {
                                cvsSplit = ",";
                            }
                            String[] bomLine = new String[2];
                            if (line.charAt(0) != '"') {
                                bomLine[0] = line.substring(0, line.indexOf(',') + 1);
                                bomLine[1] = line.substring(line.indexOf(',') + 1);
                            }else{
                                line = line.substring(1);
                                bomLine[0] = line.substring(0, line.indexOf('"'));
                                bomLine[1] = line.substring(line.indexOf('"') + 2);
                            }

                            String des = bomLine[1];
                            String newDes = "";
                            for (int i = 0; i < des.length(); i++){
                                if (des.charAt(i) != '"'){
                                    newDes += des.charAt(i);
                                }
                            }
                            des = newDes;

                            cvsSplit = ", ";
                            if (des.indexOf(", ") == -1) {
                                cvsSplit = ",";
                            }
                            String[] desLine = des.split(cvsSplit);

                            for (int i = 0; i < desLine.length; i++) {
                                int lettersFound = 0;
                                for (int l = 0; l < desLine[i].length(); l++) { //loops through each element in a line
                                    if (Character.isLetter(desLine[i].charAt(l))) {
                                        lettersFound++;
                                    }
                                }

                                if (lettersFound < 1) { //if an element is only a number

                                    String fixedDes = "";
                                    boolean stay = true;
                                    for (int l = 0; l < desLine[i - 1].length() && stay; l++) {
                                        if (Character.isLetter(desLine[i - 1].charAt(l))) {
                                            fixedDes += desLine[i - 1].charAt(l);
                                        } else {
                                            stay = !stay;
                                        }
                                    }
                                    fixedDes += desLine[i];
                                    desLine[i] = fixedDes;
                                }

                                designatorList.add(bomLine[0] + desLine[i]);
                            }

                            for (int i = 0; i < designatorList.size(); i++) {
                                String letters = "";
                                String numbers = "";
                                String finalNumbers = "";
                                String currentPart = bomLine[0];
                                String currentDesignator = designatorList.get(i).substring(designatorList.get(i).indexOf(',')+1);
                                if (currentDesignator.indexOf('-') != -1) { //if it has dashes
                                    int count = 0;
                                    while (Character.isLetter(currentDesignator.charAt(count))) {
                                        letters += currentDesignator.charAt(count);
                                        count++;
                                    }
                                    while (currentDesignator.charAt(count) != '-') {
                                        numbers += currentDesignator.charAt(count);
                                        count++;
                                    }
                                    while (!Character.isDigit(currentDesignator.charAt(count))) {
                                        count++;
                                    }
                                    finalNumbers = currentDesignator.substring(count);

                                    designatorList.set(i, currentPart + currentDesignator.substring(0, currentDesignator.indexOf('-')));
                                    int firstNum = Integer.parseInt(numbers);
                                    int secondNum = Integer.parseInt(finalNumbers);

                                    for (int k = 1; k <= secondNum - firstNum; k++) {

                                        i++;
                                        designatorList.add(i, currentPart + letters + (firstNum + k));


                                    }

                                }

                            }


                        }
                        for (int i = 0; i < designatorList.size();i++){
                            pw.println(designatorList.get(i));
                        }

                        pw.close();
                        fr.close();
                        br.close();




                    }catch (Exception ex){
                        ex.printStackTrace();
                    }





                    try {
                        //make files
                        File file = new File(fieldOne);
                        File fileTwo = fixedBom;





                        String lineRead;
                        String cvsSplit = ","; //declares character that separates file
                        int lineCount = 4; //since first 3 lines are not used
                        BufferedReader br = new BufferedReader(new FileReader(file)); //gets rid of header lines
                        for (int i = 0; i < 3; i++) {
                            br.readLine();
                        }

                        while ((lineRead = br.readLine()) != null) { //goes through lines of AMS file
                            int timesFound = 0; //sets to 0 each pattern


                            String lineNoSpaces = "";

                            if (lineRead.indexOf("       ") != -1) {

                                boolean addComma = true;
                                for (int i = 0; i < lineRead.length(); i++) {
                                    if (lineRead.charAt(i) != ' ') {
                                        lineNoSpaces += lineRead.charAt(i);
                                        addComma = true;
                                    } else if (addComma) {
                                        lineNoSpaces += ',';
                                        addComma = false;
                                    }
                                }
                            }else{
                                lineNoSpaces = lineRead;
                            }

                            if (lineNoSpaces.indexOf(",") == -1) {
                                cvsSplit = ", ";
                            }

                            String[] amsLine = lineNoSpaces.split(cvsSplit); //splits comma separated line

                            String pattern = amsLine[1]; //1 is the column of the pattern
                            String name = amsLine[9]; //9 is the column of the part name
                            String lineTwoRead; //line from BOM file to be read
                            BufferedReader brt = new BufferedReader(new FileReader(new File(fileTwo.getAbsolutePath()))); //reader for BOM file
                            int lineTwoCount = 1; //new counter, starts at 1
                            MisMatch mis = null; //placeholder object
                            while ((lineTwoRead = brt.readLine()) != null) { //goes through BOM file



                                String nameTwo = ""; //part name
                                String partTwo = ""; //pattern, but this includes a lot of spaces
                                String patTwo = ""; //pattern, without the spaces

                                if (lineTwoRead.indexOf(',') == -1) { //case where there is no comma
                                    nameTwo = lineTwoRead.substring(0, lineTwoRead.indexOf(' ')); //gets part name
                                    partTwo = lineTwoRead.substring(lineTwoRead.indexOf(' ')); //gets pattern, with all the spaces

                                    for (int l = 0; l < partTwo.length(); l++) { //gets rid of the spaces
                                        if (partTwo.charAt(l) != ' ') {
                                            patTwo += partTwo.charAt(l); //avoids spaces
                                        }
                                    }
                                } else { //case where there is a comma

                                    if (lineTwoRead.indexOf(",") == -1) {
                                        cvsSplit = ", ";
                                    }
                                    String[] lineTwo = lineTwoRead.split(cvsSplit);
                                    nameTwo = lineTwo[0];
                                    patTwo = lineTwo[1];
                                }

                                if (pattern.equalsIgnoreCase(patTwo)) { //checks if pattern from AMS equals pattern from BOM
                                    timesFound++; //keeps track of times found
                                    if (!name.equalsIgnoreCase(nameTwo)) { //case where the part names are not equal
                                        mis = new MisMatch(lineCount, lineTwoCount, name, nameTwo, pattern, patTwo); //makes a mismatch object
                                        differences.add(mis); //adds to lost
                                    }
                                }

                                lineTwoCount++; //counts line number of BOM file
                            }
                            if (timesFound == 0) { //case where pattern from AMS is not found in BOM
                                differences.add(new MisMatch(lineCount, 0, name, "Not Found", pattern, "Not Found")); //adds a not found to list
                            } else if (timesFound == 1 && mis != null) { //if found once while being a mistake
                                mis.setTimesFound(timesFound);
                            } else if (timesFound > 1 && mis == null) { //if duplicates with no mistakes
                                mis = new MisMatch(lineCount, 0, name, "Duplicate", pattern, "Duplicate");
                                mis.setTimesFound(timesFound);
                            } else if (timesFound > 1 && mis != null) { //if duplicates with a mistake
                                mis.setTimesFound(timesFound);

                                BufferedReader bufferedReader = null; //to go through BOM file again to find the line with correct info

                                bufferedReader = new BufferedReader(new FileReader(new File(fileTwo.getAbsolutePath())));

                                String check;

                                int counter = 1;
                                boolean exit = false;

                                while ((check = bufferedReader.readLine()) != null && !exit) {
                                    String nameTwo = "";
                                    String partTwo = "";
                                    String patTwo = "";
                                    if (check.indexOf(',') == -1) {
                                        nameTwo = check.substring(0, check.indexOf(' '));
                                        partTwo = check.substring(check.indexOf(' '));

                                        for (int l = 0; l < partTwo.length(); l++) {
                                            if (partTwo.charAt(l) != ' ') {
                                                patTwo += partTwo.charAt(l);
                                            }
                                        }
                                    }


                                    if (pattern.equalsIgnoreCase(patTwo) && counter != mis.getLineTwoNum()) {
                                        mis.setSecondLine(counter);
                                        exit = !exit;
                                    }

                                    counter++;
                                }

                                bufferedReader.close();
                            }


                            brt.close();
                            lineCount++;

                        }

                        try { //printing to text file
                            PrintWriter output = new PrintWriter("output.txt");
                            output.println("There are " + differences.size() + " differences");
                            output.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            output.println("File 1: " + file.getName());
                            output.println("File 2: " + fileTwo.getName());
                            output.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            for (int n = 0; n < differences.size()-1; n++){
                                for (int m = 1; m < differences.size(); m++){
                                    if (n != m && differences.get(n).getPatOne().equals(differences.get(m).getPatOne())){
                                        differences.get(n).setTimesFound(2);
                                    }
                                }
                            }
                            for (int i = 0; i < differences.size(); i++) {
                                MisMatch m = differences.get(i);
                                boolean isCorrect = true;
                                if (m.getTimesFound() > 1) {
                                    for (int c = 0; c < differences.size() && isCorrect; c++){
                                        if (differences.get(c).getLineTwoNum() == m.getSecondLine()){
                                            output.println("*Duplicates Found* Both Lines are wrong");
                                            isCorrect = !isCorrect;
                                        }
                                    }
                                    if (isCorrect) {
                                        output.println("*Duplicates Found* Correct Line in BOM File: " + m.getSecondLine());
                                    }
                                } else if (m.getTimesFound() == 0) {
                                    output.println("*Item Not Found*");
                                }else if (m.getTimesFound() == 1){
                                    output.println("*Mistake*");
                                }

                                String space22 = "                      ";
                                String space = "";
                                String component = m.getPatOne();
                                String partNo = m.getNameOne();

                                int lineNumber = m.getLineOneNum();
                                String lineNo = Integer.toString(lineNumber);
                                int longest = 29;
                                if (partNo.length() > 29){ //only one that can go longer than this
                                    longest = partNo.length() + 2;
                                }

                                while (component.length() < longest){
                                    component += ' ';
                                }
                                while (partNo.length() < longest){
                                    partNo += ' ';
                                }
                                while (lineNo.length() < longest){
                                    lineNo += ' ';
                                }

                                component += m.getPatTwo();
                                partNo += m.getNameTwo();

                                lineNo += m.getLineTwoNum();
                                String fileString = "File 1";

                                while (fileString.length() < longest){
                                    fileString += ' ';
                                }

                                output.println(space22 + fileString + "File 2");
                                output.println("ComponentID:          " + component);
                                output.println("Part Number:          " + partNo);
                                output.println("Line Number:          " + lineNo);
                                output.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            }
                            output.close();
                            Runtime rt = Runtime.getRuntime();
                            String txtPath = "output.txt";
                            Process p = rt.exec("notepad " + txtPath);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }catch (Exception ioe){
                        JOptionPane.showMessageDialog(null, "File Not Found.");
                        ioe.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Wrong File Format.");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Field Not Filled");
            }
        }
    }
}
