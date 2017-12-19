package com.lming.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shiny
 * @description
 * @date 2014-4-26
 */
public class FileUtil {

    /**
     * 校验文件是否以在指定后缀集合中
     *
     * @param filename
     * @param suffixs
     * @return
     */
    public static boolean checkFileSuffix(String filename, String[] suffixs) {
        if (suffixs.length <= 0) return true;
        for (String suffix : suffixs) {
            if (filename.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 读取某个文件夹下的指定文件，后缀为""读取所有
     *
     * @param folderpath 文件夹路径
     * @param suffixs   文件后缀
     * @return
     */
    public static List<String> getFolderFiles(String folderpath, String[] suffixs) {
        File file = new File(folderpath);
        List<String> list = new ArrayList<String>();
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            if (null == fs || fs.length == 0) {
                return list;
            }
            for (File ff : fs) {
                if (null == suffixs || suffixs.length == 0) {
                    list.add(ff.getAbsolutePath());
                } else if (checkFileSuffix(ff.getName(), suffixs)) {
                    list.add(ff.getAbsolutePath());
                }
            }
        }

        return list;
    }

    /**
     * 读取文件
     *
     * @param filepath
     * @return
     */
    public static String readFile(String filepath) {

        File file = new File(filepath);
        if (!file.exists()) {
            try {
                throw new FileNotFoundException("file not found:" + filepath);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        StringBuffer buff = new StringBuffer();
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        try {
            inputReader = new InputStreamReader(new FileInputStream(filepath));
            bufferReader = new BufferedReader(inputReader);
            String line = null;
            while ((line = bufferReader.readLine()) != null) {
                buff.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferReader.close();
                inputReader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return buff.toString();
    }


    /**
     * 将文本以行读取并每行存入list集合中
     *
     * @param filepath
     * @return
     */
    public static List<String> readFilebyline2List(String filepath) {
        File file = new File(filepath);

        if (!file.exists()) {
            try {
                throw new FileNotFoundException("file not found:" + filepath);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        List<String> list = new ArrayList<String>();
        InputStreamReader inputReader = null;
        BufferedReader bufferReader = null;
        try {
            inputReader = new InputStreamReader(new FileInputStream(filepath));
            bufferReader = new BufferedReader(inputReader);
            String line = null;
            while ((line = bufferReader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferReader.close();
                inputReader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return list;
    }

    /**
     * 删除文件夹下的所有文件，若filename非空，则只删除该文件
     *
     * @param folderpath
     * @param filename
     */
    public static void delFiles(String folderpath, String filename) {
        boolean flag = false;
        if (null == filename || "".equals(filename)) {
            flag = true;
        }

        if (flag) {

            delFolder(folderpath);
        } else {
            delFile(folderpath + File.separator + filename);
        }


    }

    /**
     * 删除文件夹下的所有文件和文件夹
     *
     * @param folderpath
     * @return
     */
    public static boolean delFolder(String folderpath) {
        try {

            File file = new File(folderpath);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    String filepath = "";
                    if (folderpath.endsWith(File.separator)) {
                        filepath = folderpath + filelist[i];
                    } else {
                        filepath = folderpath + File.separator + filelist[i];
                    }

                    File temp = new File(filepath);
                    // 是文件直接删除
                    if (temp.isFile()) {
                        temp.delete();
                        //System.out.println("delete file =>"+temp);
                    }
                    // 是目录 先删除文件
                    if (temp.isDirectory()) {
                        boolean flag = delFolder(filepath);// 先删除文件夹里面的文件
                        // 文件删除完成，在删除文件夹
                        if (flag) {
                            temp.delete();
                            //System.out.println("delete folder =>"+temp);
                        }
                    }

                }

            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 删除单一文件
     *
     * @param filepath
     * @return
     */
    public static boolean delFile(String filepath) {
        File file = new File(filepath);

        if (file.exists()) {
            file.delete();
        }

        return false;
    }


    /**
     * 写入文件
     *
     * @param content
     * @param filepath
     * @return
     */
    public static boolean writeFile(String content, String filepath) {
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(filepath);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void ifDirnotExistCreate(String filepath) {
        if (StringUtils.isEmpty(filepath)) return;

        filepath = filepath.replaceAll("\\\\", "/");

        String parentDir = filepath;
        if (filepath.lastIndexOf("/") > 0) {
            parentDir = filepath.substring(0, filepath.lastIndexOf("/"));
        }


        File dirFile = new File(parentDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
            System.out.println("目录创建成功dirpath=" + dirFile);
        }


    }


    public static void main(String[] args) {

        String[] cardRuleArray ={"621458","621036"};

        String filepath = "D:/corp_paycard_sum.csv";
        List<String> line = readFilebyline2List(filepath);
        List<String> excludeCards = new ArrayList<>();
        List<String> ruleCards = new ArrayList<>();
        for (int i = 0; i < line.size(); i++) {
            if (i > 1) {
                String cardno = line.get(i).split(",")[1];
                boolean flag = false;
                for(int j=0;j<cardRuleArray.length;j++){

                    if(cardno.startsWith(cardRuleArray[j]))
                    {
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    ruleCards.add(cardno);
                    System.out.println(cardno);
                }
                else
                {
                    excludeCards.add(cardno);
                }
            }

        }
        System.out.println("excludeCards:"+excludeCards);
    }

}
