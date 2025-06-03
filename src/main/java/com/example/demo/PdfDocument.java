package com.example.demo;

import java.util.UUID;

public class PdfDocument {
    private String id;
    private String fileName;
    private byte[] content;

    public PdfDocument(String fileName, byte[] content) {
        this.id = UUID.randomUUID().toString();
        this.fileName = fileName;
        this.content = content;
    }

    public String getId() { return id; }
    public String getFileName() { return fileName; }
    public byte[] getContent() { return content; }

    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setContent(byte[] content) { this.content = content; }
}
