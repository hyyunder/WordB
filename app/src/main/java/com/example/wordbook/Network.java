package com.example.wordbook;

import android.os.Environment;

import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Network {
    public final static String iCiBaURL1="http://dict-co.iciba.com/api/dictionary.php?w=";
    public final static String iCiBaURL2="&key=ECD5D0350FF32992184EC4DA2AF2244F";
    public final String SDPATH=Environment.getExternalStorageDirectory()+"/";

    //输入流写入文件
    public boolean saveInputStreamToFile(InputStream in, String path,String fileName ){
        File file=createSDFile(path,fileName); //相对路径即可
        int length=0;
        if(file==null)
            return true;  //其实这里的情况是文件已存在
        byte[] buffer=new byte[1024];
        FileOutputStream fOut=null;
        try {
            fOut=new FileOutputStream(file);

            while((length=in.read(buffer))!=-1){          //要利用read返回的实际成功读取的字节数，将buffer写入文件，否则将会出现错误的字节
                fOut.write(buffer, 0, length);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }finally{
            try {
                fOut.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public File createSDFile(String path,String fileName){
        File file=null;
        createSDDir(path);
        try{
            file=new File(SDPATH+path+fileName);
            if(file.exists() && file.isFile()){
                return null;
            }
            file.createNewFile();  //创建文件

        }catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }

    //创建目录,如果存在同名文件夹则返回该文件夹，否则创建文件
    public File createSDDir(String dirName){
        File dir=new File(SDPATH+dirName);
        if(dir.exists() && dir.isDirectory()){
            return dir;
        }
        dir.mkdirs();  //可创建多级文件夹
        return dir;
    }


    //这里写相对目录
    public ArrayList<String> listContentsOfFile(String path){
        ArrayList<String> list=new ArrayList<String>();
        File file=new File(SDPATH+path);
        File[] fileList=file.listFiles();
        if(fileList==null)
            return list;
        for(int i=0; i<fileList.length;i++){
            System.out.println(fileList[i].getName());
        }
        return list;
    }

    //根据输入查询单词获取word对象
    public Word getWordFromInternet(String searchedWord){
        Word word=null;
        String str=null;
        String tempWord=searchedWord;
        if(tempWord==null&& tempWord.equals(""))
            return null;
        char[] array=tempWord.toCharArray();
        if(array[0]>256)           //是中文，或其他语言的的简略判断
            tempWord="_"+URLEncoder.encode(tempWord);
        try{
            //获得查询单词的输入流
            String tempUrl=iCiBaURL1+tempWord+iCiBaURL2;
            InputStream tempInput=null;
            URL url=null;
            HttpURLConnection connection=null;
            //设置超时时间
            try{
                url=new URL(tempUrl);
                connection=(HttpURLConnection)url.openConnection();     //别忘了强制类型转换
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                tempInput=connection.getInputStream();
            }catch(Exception e){
                e.printStackTrace();
            }
            if(tempInput!=null){
                saveInputStreamToFile(tempInput, "", "word.txt");
                XmlAnalyze xmlParser = new XmlAnalyze();//创建SAX解析器
                InputStreamReader reader = new InputStreamReader(tempInput, "utf-8");  //最终目的获得一个InputSource对象用于传入形参
                XmlHandler handler = new XmlHandler();//创建处理函数
                xmlParser.parseJinShanXml(handler, new InputSource(reader));//开始解析
                word = handler.getWord();
                word.setWord(searchedWord);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return word;
    }

}
