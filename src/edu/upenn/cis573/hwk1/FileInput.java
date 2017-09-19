package edu.upenn.cis573.hwk1;

import java.io.*;

/**
 * Created by Carlton on 9/9/17.
 */
class FileInput {

    // fields
    private File[] fileList;
    private int fileListLength;
    private int[][] charFreqList;

    /*
     Opens directory and load files into an array.
     */
    private void openDir(String[] argv){

        // check number of arguments
        if (argv.length==0) {
            throw new AnalysisException("No argument provided!");
        } else if (argv.length>1) {
            throw new AnalysisException("More than one argument provided. Please enter only one argument.");
        }

        // search directory
        String path= argv[0];
        File folder=  new File(path);

        if (!folder.exists()) {
            throw new AnalysisException("Input path: "+ path+ " does not exist.");
        } else if (!folder.canRead()){
            throw new AnalysisException("Directory can not be read due to lack of permission.");
        } else if (folder.list().length== 0){
            throw new AnalysisException("Directory is empty!");
        }

        // check each file
        fileList= folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return !file.isHidden();
            }
        });
        for (File file: fileList){
            if (!file.canRead()){
                throw new AnalysisException("File: "+ file+" is not readable!");
            }
        }
        fileListLength= fileList.length;
        charFreqList = new int[fileListLength+1][26];
    }

    /*
     wrapper class for open directory
     */
    public void openSource(String[] argv) {
        openDir(argv);
    }

    /*
     load all files
     */
    public void loadAllFiles() throws IOException{
        int pos= 0;
        for (File file: fileList){
            loadFile(file, pos++);
        }
    }

    /*
     load plain text file
     */
    private void loadFile(File file, int pos) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                for (char ch: line.toCharArray()){
                    if (Character.isAlphabetic(ch)){
                        int chLower= Character.toLowerCase(ch)- 'a';
                        charFreqList[fileListLength][chLower]++;
                        charFreqList[pos][chLower]++;
                    }
                }
            }
        }
    }

    /*
     access method for frequency map
     */
    public int[] getFrequency(int idx){
        if (idx> fileListLength) {
            throw new AnalysisException("File index out of bound");
        }
        return this.charFreqList[idx];
    }

    /*
     subtract one file's count of each letter from the sum of all counts
     */
    public void subtractFrequency(int pos){
        for (int i=0 ;i < 26; i++){
            charFreqList[fileListLength][i]-= charFreqList[pos][i];
        }
    }

    /*
     restore one file's count of each letter to the sum of all counts
     */
    public void restoreFrequency(int pos) {
        for (int i=0; i< 26; i++){
            charFreqList[fileListLength][i]+= charFreqList[pos][i];
        }
    }

    /*
     access method for file list length
     */
    public int getFileListLength(){
        return fileListLength;
    }

    /*
     access method for file list
     */
    public File[] getFileList(){
        return fileList;
    }

    /*
     print all array
     */
    public void printAr(int[] ar){
        for (int i=0; i< ar.length; i++) {
            System.out.print(ar[i]+" ");
        }
        System.out.println();
    }
}
