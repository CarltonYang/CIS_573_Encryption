package edu.upenn.cis573.hwk1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;

import static org.junit.Assert.*;
/**
 * Created by Carlton on 9/11/17.
 */
public class AllTest {

    private FileInput lf;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void relativePath(){
        String[] argv= new String[1];
        argv[0]= "./corpus";
        try {
            lf = new FileInput();
            lf.openSource(argv);
        } catch (AnalysisException e){
            //expected
        }
    }

    @Test
    public void passNoArgu() {
        String[] empty= new String[0];
        try {
            lf = new FileInput();
            lf.openSource(empty);
        } catch (AnalysisException e){
            //expected
        }
    }

    @Test
    public void passMultiArgu() {
        String[] argv= new String[5];
        try {
            lf = new FileInput();
            lf.openSource(argv);
        } catch (AnalysisException e){
            //expected
        }
    }

    @Test
    public void invalidDir(){
        String[] argv= new String[1];
        argv[0]= "/Users/Carlton/UPenn/573_Encryption/NoSuchDir";
        try {
            lf = new FileInput();
            lf.openSource(argv);
        } catch (AnalysisException e){
            //expected
        }
    }

    @Test
    public void emptyDir(){
        String[] argv= new String[1];
        argv[0]= "/Users/Carlton/UPenn/573_Encryption/emptyDir";
        try {
            lf = new FileInput();
            lf.openSource(argv);
        } catch (AnalysisException e){
            //expected
        }
    }

    @Test
    public void validDir() {
        String[] argv = new String[1];
        argv[0] = "/Users/Carlton/UPenn/573_Encryption/fileDir";
        try {
            lf = new FileInput();
            lf.openSource(argv);
        } catch (AnalysisException e) {
            fail("Should have not raised exception!");
        }
    }

    @Test
    public void validFile() {
        String[] argv = new String[1];
        argv[0] = "/Users/Carlton/UPenn/573_Encryption/fileDir";
        try {
            lf = new FileInput();
            lf.openSource(argv);
            assertEquals(lf.getFileListLength(), 2);
            try {
                lf.loadAllFiles();
                assertEquals(lf.getFrequency(0)[0], 6);
                assertEquals(lf.getFrequency(0)[1], 8);
                assertEquals(lf.getFrequency(0)[2], 11);
                assertEquals(lf.getFrequency(2)[1], 15);
            } catch (IOException e) {
                fail("Should have not raised exception!");
            }
        } catch (AnalysisException e) {
            fail("Should have not raised exception!");
        }
    }

    @Test
    public void validCipher(){
        String[] argv = new String[1];
        argv[0] = "/Users/Carlton/UPenn/573_Encryption/fileDir";
        try {
            lf = new FileInput();
            lf.openSource(argv);
            assertEquals(lf.getFileListLength(), 2);
            try {
                lf.loadAllFiles();
                AnalysisResult ar= new AnalysisResult(lf.getFileListLength());
                ar.setFileNameList(lf.getFileList());

                Cipher cipher= new SubstitutionCipher();
                cipher.setKey(2);
                CrossValidation cv= new CrossValidation(lf, ar, cipher);
                int[] cipherMap = new int[26];
                cv.frequencyAnalysis(0, cipherMap);
                assertEquals(cipherMap[2], 2);
                assertEquals(cipherMap[3], 1);
                assertEquals(cipherMap[4], 0);

                assertEquals(cipherMap[cipher.cipher(1)], 1);
                assertEquals(cipherMap[cipher.cipher(2)], 0);

                ar.printResult();
                String correctOutput = "test1.txt: 8 correct, 17 incorrect\n" +
                        "test2.txt: 0 correct, 0 incorrect\n" +
                        "Total: 8 correct, 17 incorrect\n" +
                        "Accuracy: 32.00%";
//                assertEquals(correctOutput, outContent.toString());
            } catch (IOException e) {
                fail("Should have not raised exception!");
            }

        } catch (AnalysisException e) {
            fail("Should have not raised exception!");
        }
    }

}