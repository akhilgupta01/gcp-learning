package com.examples.gcp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

public class StorageExamples {
    public static void main(String[] args) throws Exception {
        String jsonPath = "C:\\Users\\Akhil\\keys\\ag-trial-project-1-37f7d97087f1.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));

        String PROJECT_ID = "ag-trial-project-1";
        String BUCKET_NAME = "ag-trial-project-1_work_dir";
        String OBJECT_NAME = "incoming/passengers.csv";

        StorageOptions options = StorageOptions.newBuilder()
                .setProjectId(PROJECT_ID)
                .setCredentials(credentials)
                .build();

        Storage storage = options.getService();
        Blob blob = storage.get(BUCKET_NAME, OBJECT_NAME);
        String fileContent = new String(blob.getContent());
        System.out.println(fileContent);
    }
}
