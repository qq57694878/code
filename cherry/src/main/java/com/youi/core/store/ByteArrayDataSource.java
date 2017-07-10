package com.youi.core.store;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ByteArrayDataSource implements DataSource {
    private String name;
    private byte[] bytes;

    public ByteArrayDataSource(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public ByteArrayDataSource(byte[] bytes) {
        this("bytes", bytes);
    }

    public String getName() {
        return name;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    public OutputStream getOutputStream() {
        throw new IllegalStateException("not support");
    }

    public String getContentType() {
        return "";
    }
}