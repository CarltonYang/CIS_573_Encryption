package edu.upenn.cis573.hwk1;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Carlton on 9/11/17.
 */
public class AnalysisResult {

    // fields
    String[] fileNameList;
    int[] totalCharCount;
    int[] corCharCount;

    /*
     constructor with file list length
     */
    public AnalysisResult(int fileListLength){
        totalCharCount= new int[fileListLength];
        corCharCount= new int[fileListLength];
    }

    /*
     print out all the results
     */
    public void printResult(){
        int sum=0, cor=0;
        for (int i=0; i< fileNameList.length; i++){
            System.out.print(fileNameList[i] +": ");
            System.out.print(corCharCount[i] + " correct, ");
            System.out.println((totalCharCount[i]- corCharCount[i]) + " incorrect");
            sum+= totalCharCount[i];
            cor+= corCharCount[i];
        }
        System.out.print("Total: ");
        System.out.print(cor + " correct, ");
        System.out.println((sum-cor) + " incorrect");
        System.out.print("Accuracy: ");
        DecimalFormat df = new DecimalFormat("#.00");

        System.out.println(df.format((double)cor/sum* 100) +"%");
    }

    /*
     set list of total number of characters of one file
     */
    public void setTotalCount(int index, int count){
        totalCharCount[index]= count;
    }

    /*
     set list of number of correctly deciphered characters of one file
     */
    public void setCorCount(int index, int count){
        corCharCount[index]= count;
    }

    /*
     set list of file names
     */
    public void setFileNameList(File[] list){
        fileNameList= new String[list.length];
        for (int i=0; i< list.length; i++){
            String fullPath= list[i].toString();
            fileNameList[i]= fullPath.substring(fullPath.lastIndexOf("/")+1, fullPath.length());
        }
    }
}
