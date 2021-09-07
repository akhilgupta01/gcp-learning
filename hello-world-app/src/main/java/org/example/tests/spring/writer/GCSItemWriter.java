package org.example.tests.spring.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;
import org.springframework.core.io.WritableResource;

import java.io.OutputStream;
import java.util.List;

public class GCSItemWriter<T> implements ItemWriter<T> {

    private WritableResource resource;

    public GCSItemWriter(GoogleStorageResource resource) {
        this.resource = resource;
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        try (OutputStream outputStream = resource.getOutputStream()) {
            for (T item : items) {
                outputStream.write(String.valueOf(item).getBytes());
            }
        }
    }
}
