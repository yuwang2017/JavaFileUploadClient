package net.codejava.networking;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 * @author www.codejava.net
 *
 */
public class MultipartFileUploader {

    static String fileFolderPath = "c:/CM";

    public static void main(String[] args) {
        File fileFolder = new File(fileFolderPath);
        File[] files = fileFolder.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.toLowerCase().indexOf(".mp3") > 0) {
                uploadFileWithMyCode(file);
            }
        }
    }

    public static void uploadFileWithMyCode(File zip){
        GoogleCloudTool up = new GoogleCloudTool();

        try {
            up.uploadFile(zip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(File uploadFile) {
        String charset = "UTF-8";
        String fileName = uploadFile.getName();
        System.out.println(fileName);
        String requestURL = "https://surveycataudioprocessor.ue.r.appspot.com/uploadAudio";

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");

            multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("keywords", "Java,upload,Spring");

            multipart.addFilePart("fileUpload", uploadFile);
            multipart.addFormField("fileName", fileName);

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}