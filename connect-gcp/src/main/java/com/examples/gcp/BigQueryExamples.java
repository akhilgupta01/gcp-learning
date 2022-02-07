package com.examples.gcp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.StreamSupport;

public class BigQueryExamples {
    public static void main(String[] args) {
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "C:\\Users\\Akhil\\keys\\ag-trial-project-1-37f7d97087f1.json");
        String query = "SELECT a.*, b.name as gate_embarked " +
                "FROM `ag-trial-project-1.transactions_dataset.passengers` a, " +
                "`ag-trial-project-2.common_reference_dataset.gates` b " +
                "where a.Embarked = b.code " +
                "LIMIT 20";
        query(query);
    }

    public static void query(String query) {
        try {
            String jsonPath = "C:\\Users\\Akhil\\keys\\ag-trial-project-1-37f7d97087f1.json";
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath));

            BigQuery bigquery = BigQueryOptions
                    .newBuilder().setCredentials(credentials).build().getService();
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
            TableResult results = bigquery.query(queryConfig);
            StreamSupport.stream(results.iterateAll().spliterator(), false)
                    .map(BigQueryExamples::passenger)
                    .forEach(System.out::println);
            //.map(row -> passenger(row))

            System.out.println("Query performed successfully.");
        } catch (BigQueryException | InterruptedException | IOException e) {
            System.out.println("Query not performed \n" + e.toString());
        }
    }

    private static Passenger passenger(FieldValueList row) {
        return Passenger.builder()
                .passengerId(row.get("PassengerId").getStringValue())
                .name(row.get("Name").getStringValue())
                .survived(row.get("Survived").getLongValue() == 1)
                .embarked(row.get("gate_embarked").getStringValue())
                .build();
    }

}
