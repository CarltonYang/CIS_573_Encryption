package edu.upenn.cis573.hwk1;

import java.io.IOException;

/**
 * Created by Carlton on 9/9/17.
 */
public class Main {
    public static void main(String[] argv) throws IOException{

        // populate data structure from directory
        FileInput fa= new FileInput();
        fa.openSource(argv);
        fa.loadAllFiles();

        // initialize result data structure
        AnalysisResult ar= new AnalysisResult(fa.getFileListLength());
        ar.setFileNameList(fa.getFileList());

        // initialize new substitution cipher
        Cipher cipher= new SubstitutionCipher();
        cipher.setKey(2);

        // perform cross validation
        CrossValidation cv= new CrossValidation(fa, ar, cipher);
        cv.crossValidate();

        // print results
        ar.printResult();
    }
}
