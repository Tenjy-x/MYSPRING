package Tendry.Utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MapFile {
    private String filename;
    private String extension;
    private Byte[] bytes;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Byte[] getBytes() {
        return bytes;
    }

    public void setBytes(Byte[] bytes) {
        this.bytes = bytes;
    }

    public void setBytes(byte[] bytes) {
        if (bytes == null) {
            this.bytes = null;
            return;
        }
        this.bytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            this.bytes[i] = bytes[i];
        }
    }

    public void saveTo(String directory) throws IOException {
        if (this.bytes == null || this.filename == null || this.filename.trim().isEmpty()) {
            return;
        }
        if (directory == null || directory.trim().isEmpty()) {
            throw new IOException("Le répertoire de téléversement est vide");
        }

        Path targetDir = Path.of(directory).toAbsolutePath().normalize();
        Files.createDirectories(targetDir);
        String safeFilename = this.filename.replace('\\', '/');
        Path targetFile = targetDir.resolve(safeFilename).normalize();
        if (!targetFile.startsWith(targetDir) || targetFile.equals(targetDir)) {
            throw new IOException("Nom de fichier de téléversement invalide");
        }

        byte[] raw = new byte[this.bytes.length];
        for (int i = 0; i < this.bytes.length; i++) {
            raw[i] = this.bytes[i];
        }
        try (FileOutputStream fos = new FileOutputStream(targetFile.toFile())) {
            fos.write(raw);
        }
    }

    public static MapFile fromInputStream(String fieldName, InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        String fileName = fieldName;
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "upload_" + System.currentTimeMillis();
        }
        String cleanName = fileName.replace('\\', '/');
        int lastSlash = Math.max(cleanName.lastIndexOf('/'), cleanName.lastIndexOf('\\'));
        if (lastSlash >= 0) {
            cleanName = cleanName.substring(lastSlash + 1);
        }

        MapFile mapFile = new MapFile();
        mapFile.setFilename(cleanName);
        int extensionIndex = cleanName.lastIndexOf('.');
        if (extensionIndex >= 0 && extensionIndex < cleanName.length() - 1) {
            mapFile.setExtension(cleanName.substring(extensionIndex + 1));
        }

        byte[] data = inputStream.readAllBytes();
        mapFile.setBytes(data);
        return mapFile;
    }
}
