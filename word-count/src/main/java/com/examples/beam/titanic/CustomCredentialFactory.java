package com.examples.beam.titanic;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.apache.beam.sdk.extensions.gcp.auth.GcpCredentialFactory;
import org.apache.beam.sdk.options.PipelineOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CustomCredentialFactory extends GcpCredentialFactory {

    private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/cloud-platform", "https://www.googleapis.com/auth/devstorage.full_control", "https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/datastore", "https://www.googleapis.com/auth/bigquery", "https://www.googleapis.com/auth/bigquery.insertdata", "https://www.googleapis.com/auth/pubsub");
    private static CustomCredentialFactory INSTANCE = new CustomCredentialFactory();

    private CustomCredentialFactory() {
    }

    /**
     * Required by GcpOptions.GcpUserCredentialsFactory#create(org.apache.beam.sdk.options.PipelineOptions)
     */
    public static CustomCredentialFactory fromOptions(PipelineOptions o) {
        return INSTANCE;
    }

    @Override
    public Credentials getCredential() {
        Credentials credentials = null;
        try {
            String jsonPath = "C:\\Users\\Akhil\\keys\\ag-trial-project-a-641e1d3dd625.json";
            credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath)).createScoped(SCOPES);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  credentials;
    }
}