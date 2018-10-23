package com.example.wordbook;

public class MyThread extends Thread {
    private Word word;
    private String text;
    public MyThread(String text) {
        this.text=text;
    }

    @Override
    public void run() {
        Network  n =new Network();
        word = n.getWordFromInternet(text);
    }
    public Word getWord(){
        return word;
    }
}
