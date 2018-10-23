package com.example.wordbook;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

public class Word {
    String word;
    String proB;
    String musicB;
    String proA;
    String musicA;
    String message;
    String exampleE;
    String exampleC;

    public Word(){
        super();
        this.word="";
        this.proB="";
        this.musicB="";
        this.proA="";
        this.musicA="";
        this.message="";
        this.exampleE="";
        this.exampleC="";
    }

    public Word(String word, String proB,String musicB,String proA,String musicA,String message,String exampleE,String exampleC){
        this.word=word;
        this.proB=proB;
        this.musicB=musicB;
        this.proA=proA;
        this.musicA=musicA;
        this.message=message;
        this.exampleE=exampleE;
        this.exampleC=exampleC;
    }
    public ArrayList<String> getExampleEList(){
        ArrayList<String> list=new ArrayList<String>();
        BufferedReader br=new BufferedReader(new StringReader(this.exampleE));
        String str=null;
        try{
            while((str=br.readLine())!=null){
                list.add(str);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getExampleCList(){
        ArrayList<String> list=new ArrayList<String>();
        BufferedReader br=new BufferedReader(new StringReader(this.exampleC));
        String str=null;
        try{
            while((str=br.readLine())!=null){
                list.add(str);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void setword(Word w){
        if(w!=null) {
            this.setWord(w.getWord());
            this.setProA(w.getProA());
            this.setMusicA(w.getMusicA());
            this.setProB(w.getProB());
            this.setMusicB(w.getMusicB());
            this.setMessage(w.getMessage());
            this.setExampleE(w.getExampleE());
            this.setExampleC(w.getExampleC());
        }else{
            System.out.println("2222222222222222");
        }
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getProB() {
        return proB;
    }

    public void setProB(String proB) {
        this.proB = proB;
    }

    public String getMusicB() {
        return musicB;
    }

    public void setMusicB(String musicB) {
        this.musicB = musicB;
    }

    public String getProA() {
        return proA;
    }

    public void setProA(String proA) {
        this.proA = proA;
    }

    public String getMusicA() {
        return musicA;
    }

    public void setMusicA(String musicA) {
        this.musicA = musicA;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExampleE() {
        return exampleE;
    }

    public void setExampleE(String exampleE) {
        this.exampleE = exampleE;
    }

    public String getExampleC() {
        return exampleC;
    }

    public void setExampleC(String exampleC) {
        this.exampleC = exampleC;
    }

    public void print(){
        System.out.println("word="+this.word);
        System.out.println("proB="+this.proB);
        System.out.println("musicB="+this.musicB);
        System.out.println("proA="+this.proA);
        System.out.println("musicA="+this.musicA);
        System.out.println("message="+this.message);
        System.out.println("exampleE="+this.exampleE);
        System.out.println("exampleC="+this.exampleC);
    }
}
