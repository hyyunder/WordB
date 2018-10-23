package com.example.wordbook;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler {

    public Word word=null;
    private String tagName=null;
    private String interpret="";       //防止空指针异常
    private String orig="";
    private String trans="";
    private boolean isChinese=false;
    public XmlHandler(){
        word=new Word();
        isChinese=false;
    }

    public Word getWord(){
        return word;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        if(length<=0)
            return;
        for(int i=start; i<start+length; i++){
            if(ch[i]=='\n')
                return;
        }

        //去除莫名其妙的换行！

        String str=new String(ch,start,length);
        if(tagName=="key"){
            word.setWord(str);
        }else if(tagName=="ps"){
            if(word.getProB().length()<=0){
                word.setProB(str);
            }else{
                word.setProA(str);
            }
        }else if(tagName=="pron"){
            if(word.getMusicB().length()<=0){
                word.setMusicB(str);
            }else{
                word.setMusicA(str);
            }
        }else if(tagName=="pos"){
            isChinese=false;
            interpret=interpret+str+" ";
        }else if(tagName=="acceptation"){
            interpret=interpret+str+"\n";
            interpret=word.getMessage()+interpret;
            word.setMessage(interpret);
            interpret=""; //初始化操作，预防有多个释义
        }else if(tagName=="orig"){


            orig=word.getExampleE();
            word.setExampleE(orig+str+"\n");


        }else if(tagName=="trans"){
            String temp=word.getExampleC()+str+"\n";
            word.setExampleC(temp);

        }else if(tagName=="fy"){
            isChinese=true;
            word.setMessage(str);
        }


    }



    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        // TODO Auto-generated method stub
        super.endElement(uri, localName, qName);
        tagName=null;


    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
        super.startElement(uri, localName, qName, attributes);
        tagName=localName;
    }

    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.endDocument();
        if(isChinese)
            return;
        String interpret=word.getMessage();
        if(interpret!=null && interpret.length()>0){
            char[] strArray=interpret.toCharArray();
            word.setMessage(new String(strArray,0,interpret.length()-1));
            //去掉解释的最后一个换行符
        }

    }
}
