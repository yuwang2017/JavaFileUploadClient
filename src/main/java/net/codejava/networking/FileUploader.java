package net.codejava.networking;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploader {
    public void uploadFile(File zip){
        String zipFile = zip.getAbsolutePath();
        String serverUrl = "https://surveycataudioprocessor.ue.r.appspot.com/upload";
        long fileSize = zip.length();
        long uploadSize = 0;
        try {
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;

            if (zip.isFile()) {

                // open a URL connection to the Servlet
                URL url = new URL(serverUrl);

                // Open a HTTP connection to the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", zipFile);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + zipFile + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                FileInputStream fileInputStream = new FileInputStream(zip);

                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    uploadSize = uploadSize + bufferSize;
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                int serverResponseCode = conn.getResponseCode();
                fileInputStream.close();
                dos.flush();
                dos.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sbuff = new StringBuffer();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    sbuff.append(inputLine);
                }
                br.close();
                System.out.println(sbuff.toString());
            }
        } catch (Exception e) {
           e.printStackTrace();
        }

    }
}
