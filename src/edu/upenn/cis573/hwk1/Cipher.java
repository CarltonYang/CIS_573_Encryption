package edu.upenn.cis573.hwk1;

/**
 * Created by Carlton on 9/10/17.
 */
public interface Cipher {

    /*
     return key of the cipher
     */
    int getKey();

    /*
     set key of the cipher
     */
    void setKey(int key);

    /*
     cipher text/char based on the key
     */
    char cipher(int charToCipher);

    /*
     decipher text/char based on the key
     */
    char decipher(int charToDeCipher);
}
