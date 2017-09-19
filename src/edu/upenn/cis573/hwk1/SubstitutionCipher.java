package edu.upenn.cis573.hwk1;

/**
 * Created by Carlton on 9/11/17.
 */
public class SubstitutionCipher implements Cipher {

    public int key;
    
    public int getKey(){
        return key;
    }

    public void setKey(int key){
        this.key= key;
    }

    public char cipher(int charToCipher){
        return (char)((charToCipher+key) % 26);
    }

    public char decipher(int chartoDecipher){
        return (char)((chartoDecipher-key) % 26);
    }
}
