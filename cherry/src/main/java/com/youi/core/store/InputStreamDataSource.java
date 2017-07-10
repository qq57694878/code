package com.youi.core.store;

import javax.activation.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamDataSource implements DataSource {
    private String name;
    private InputStream inputStream;

    public InputStreamDataSource(String name, InputStream inputStream) {
        this.name = name;
        this.inputStream = inputStream;
    }

    public InputStreamDataSource(InputStream inputStream) {
        this("inputStream", inputStream);
    }

    public String getName() {
        return name;
    }

    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        throw new IllegalStateException("not support");
    }

    public String getContentType() {
        return "";
    }
}
