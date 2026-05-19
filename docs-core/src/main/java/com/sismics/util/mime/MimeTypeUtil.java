package com.sismics.util.mime;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility to check MIME types.
 *
 * @author bgamard
 */
public class MimeTypeUtil {
    /**
     * Try to guess the MIME type of a file.
     * 
     * @param file File to inspect
     * @param name File name
     * @return MIME type
     * @throws IOException e
     */
    public static String guessMimeType(Path file, String name) throws IOException {
        // Check extension first for all known types.
        // Files.probeContentType() delegates to the OS registry on Windows, which
        // maps several extensions incorrectly (e.g. .csv→Excel, .zip→x-zip-compressed).
        if (name != null) {
            String lower = name.toLowerCase();
            if (lower.endsWith(".csv"))  return MimeType.TEXT_CSV;
            if (lower.endsWith(".zip"))  return MimeType.APPLICATION_ZIP;
            if (lower.endsWith(".pdf"))  return MimeType.APPLICATION_PDF;
            if (lower.endsWith(".png"))  return MimeType.IMAGE_PNG;
            if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return MimeType.IMAGE_JPEG;
            if (lower.endsWith(".gif"))  return MimeType.IMAGE_GIF;
            if (lower.endsWith(".txt"))  return MimeType.TEXT_PLAIN;
            if (lower.endsWith(".webm")) return MimeType.VIDEO_WEBM;
            if (lower.endsWith(".mp4"))  return MimeType.VIDEO_MP4;
            if (lower.endsWith(".odt"))  return MimeType.OPEN_DOCUMENT_TEXT;
            if (lower.endsWith(".docx")) return MimeType.OFFICE_DOCUMENT;
            if (lower.endsWith(".pptx")) return MimeType.OFFICE_PRESENTATION;
            if (lower.endsWith(".xlsx")) return MimeType.OFFICE_SHEET;
        }

        String mimeType = Files.probeContentType(file);

        if (mimeType == null && name != null) {
            mimeType = URLConnection.getFileNameMap().getContentTypeFor(name);
        }

        if (mimeType == null) {
            return MimeType.DEFAULT;
        }

        return mimeType;
    }
    
    /**
     * Get a file extension linked to a MIME type.
     * 
     * @param mimeType MIME type
     * @return File extension
     */
    public static String getFileExtension(String mimeType) {
        switch (mimeType) {
            case MimeType.APPLICATION_ZIP:
                return "zip";
            case MimeType.IMAGE_GIF:
                return "gif";
            case MimeType.IMAGE_JPEG:
                return "jpg";
            case MimeType.IMAGE_PNG:
                return "png";
            case MimeType.APPLICATION_PDF:
                return "pdf";
            case MimeType.OPEN_DOCUMENT_TEXT:
                return "odt";
            case MimeType.OFFICE_DOCUMENT:
                return "docx";
            case MimeType.TEXT_PLAIN:
                return "txt";
            case MimeType.TEXT_CSV:
                return "csv";
            case MimeType.VIDEO_MP4:
                return "mp4";
            case MimeType.VIDEO_WEBM:
                return "webm";
            default:
                return "bin";
        }
    }
}
