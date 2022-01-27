package com.examples.beam.titanic;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.apache.beam.sdk.extensions.gcp.auth.GcpCredentialFactory;
import org.apache.beam.sdk.options.PipelineOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class CustomCredentialFactory extends GcpCredentialFactory {

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
            String jsonPath = "C:\\Users\\Akhil\\keys\\ag-trial-project-1-37f7d97087f1.json";
            credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  credentials;
    }
}