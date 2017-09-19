package edu.upenn.cis573.hwk1;

import java.io.IOException;
import java.util.PriorityQueue;

/**
 * Created by Carlton on 9/7/17.
 */
public class CrossValidation {

    /*
    fields of Frequency Analysis Class
     */
    FileInput fa;
    Cipher cipher;
    AnalysisResult as;

    /*
    private inner class for priority queue
     */
    private class Pair{
        char ch;
        int count;

        public Pair(char ch, int count){
            this.ch= ch;
            this.count = count;
        }

        public String toString(){
            return ((char)(ch+'a')+" "+ count);
        }
    }

    /*
     constructor
     */
    public CrossValidation(FileInput fa, AnalysisResult ar, Cipher cipher) {
        this.fa= fa;
        this.as= ar;
        this.cipher= cipher;
    }

    /*
     perform cross validation to all files
     */
    public void crossValidate() throws IOException{

        // initialize map for frequency map
        for (int i=0; i< fa.getFileListLength(); i++){
            int[] cipherMap= new int[26];
            frequencyAnalysis(i, cipherMap);
        }
    }


    /*
     perform frequency analysis to one file
     */
    public void frequencyAnalysis(int pos, int[] cipherMap){
        PriorityQueue<Pair> sumPQ= new PriorityQueue<>((o1, o2) -> o1.count- o2.count);
        PriorityQueue<Pair> oriPQ= new PriorityQueue<>((o1, o2) -> o1.count- o2.count);
        int[] sumCount = fa.getFrequency(fa.getFileListLength());
        int[] textCount= fa.getFrequency(pos);

        fa.subtractFrequency(pos);
        for (int i=0 ;i< 26; i++){
            sumPQ.add(new Pair((char)i, sumCount[i]));
            oriPQ.add(new Pair(cipher.cipher(i), textCount[i]));
        }
        fa.restoreFrequency(pos);

        // establish frequency model
        while (!oriPQ.isEmpty()){
            cipherMap[oriPQ.poll().ch]= sumPQ.poll().ch;
        }

        int total=0, correct=0;
        for (int i=0; i< 26; i++){
            total+= textCount[i];

            // decipher
            int decipheredChar= cipherMap[cipher.cipher(i)];

            // compare with original character
            if ( decipheredChar== i) correct+= textCount[i];
        }
        as.setTotalCount(pos, total);
        as.setCorCount(pos, correct);
    }


}
