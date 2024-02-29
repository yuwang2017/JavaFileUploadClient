package net.codejava.networking;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GoogleCloudTool {
    public void uploadFile(File file) throws Exception {
        Storage storage = StorageOptions.getDefaultInstance().getService();
      /*  Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("path/to/file"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId("baeldung-cloud-tutorial").build().getService();

       */

        Bucket bucket = storage.get("survey_cat_audio_files2");
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(byteArray);
        }
        Blob blob = bucket.create(file.getName(), byteArray);

    }
}
