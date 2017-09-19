package edu.upenn.cis573.hwk1;

/**
 * Created by Carlton on 9/11/17.
 */
public class AnalysisException extends RuntimeException{
    public AnalysisException(String message) {
        super(message);
    }

    public AnalysisException(Exception e) {
        super(e);
    }
}
