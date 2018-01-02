package Comparator;

/**
 * Created by rahul on 2017-07-04.
 */
public class MisMatch {


    private int lineOneNum;
    private int lineTwoNum;
    private String nameOne;
    private String nameTwo;
    private String patOne;
    private String patTwo;
    private int timesFound = 0;
    private int secondLine;



    MisMatch(int lOne, int lTwo, String nOne, String nTwo, String pOne, String pTwo){
        lineOneNum = lOne;
        lineTwoNum = lTwo;
        nameOne = nOne;
        nameTwo = nTwo;
        patOne = pOne;
        patTwo = pTwo;
    }

    public int getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(int secondLine) {
        this.secondLine = secondLine;
    }

    public int getTimesFound() {
        return timesFound;
    }

    public void setTimesFound(int timesFound) {
        this.timesFound = timesFound;
    }

    public int getLineOneNum() {
        return lineOneNum;
    }

    public void setLineOneNum(int lineOneNum) {
        this.lineOneNum = lineOneNum;
    }

    public int getLineTwoNum() {
        return lineTwoNum;
    }

    public void setLineTwoNum(int lineTwoNum) {
        this.lineTwoNum = lineTwoNum;
    }

    public String getNameOne() {
        return nameOne;
    }

    public void setNameOne(String nameOne) {
        this.nameOne = nameOne;
    }

    public String getNameTwo() {
        return nameTwo;
    }

    public void setNameTwo(String nameTwo) {
        this.nameTwo = nameTwo;
    }

    public String getPatOne() {
        return patOne;
    }

    public void setPatOne(String patOne) {
        this.patOne = patOne;
    }

    public String getPatTwo() {
        return patTwo;
    }

    public void setPatTwo(String patTwo) {
        this.patTwo = patTwo;
    }
}
