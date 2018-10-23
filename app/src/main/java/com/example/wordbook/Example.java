package com.example.wordbook;

public class Example {
    private String exampleE;
    private String exampleC;

    public Example(){
    }

    public Example(String exampleE,String exampleC){
        this.exampleE=exampleE;
        this.exampleC=exampleC;
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
}
