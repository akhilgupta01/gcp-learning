package org.example.tests.spring.writer;

import lombok.SneakyThrows;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.cloud.gcp.storage.GoogleStorageResource;
import org.springframework.core.io.WritableResource;

import java.io.OutputStream;
import java.util.List;

public class GCSItemWriter<T> extends AbstractItemStreamItemWriter<T> {

    private WritableResource resource;

    private OutputStream outputStream;

    public GCSItemWriter(GoogleStorageResource resource) {
        this.resource = resource;
    }

    @Override
    @SneakyThrows
    public void close() {
        outputStream.close();
    }

    @Override
    @SneakyThrows
    public void open(ExecutionContext executionContext) {
        outputStream = resource.getOutputStream();
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        for(T item: items){
            outputStream.write(String.valueOf(item).getBytes());
        }
    }
}
