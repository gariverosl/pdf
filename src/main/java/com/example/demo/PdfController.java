package com.example.demo;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pdfs")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Only PDF files are allowed");
        }
        PdfDocument doc = pdfService.save(file.getOriginalFilename(), file.getBytes());
        return ResponseEntity.ok("Uploaded PDF with ID: " + doc.getId());
    }

    @GetMapping
    public List<String> listPdfs() {
        return pdfService.findAll().stream()
                .map(doc -> doc.getId() + " - " + doc.getFileName())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String id) {
        PdfDocument doc = pdfService.findById(id);
        if (doc == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(doc.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePdf(@PathVariable String id) {
        boolean deleted = pdfService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Deleted PDF with ID: " + id);
    }
}