package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PdfService {
    private final Map<String, PdfDocument> storage = new ConcurrentHashMap<>();

    public PdfDocument save(String fileName, byte[] content) {
        PdfDocument doc = new PdfDocument(fileName, content);
        storage.put(doc.getId(), doc);
        return doc;
    }

    public Collection<PdfDocument> findAll() {
        return storage.values();
    }

    public PdfDocument findById(String id) {
        return storage.get(id);
    }

    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}
