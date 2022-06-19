package main.java.com.platon.entity;

import java.io.*;


public class IVFSInputStream extends FileInputStream {

    private static volatile File file;

    private final FileInputStream fileInputStream;

    public IVFSInputStream(FileInputStream inputStream) throws FileNotFoundException {
        super(getFile());
        this.fileInputStream = inputStream;
    }

    public static File getFile() {
        return file;
    }


    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public static void setFile(File file) {
        IVFSInputStream.file = file;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return fileInputStream.read(b, off, len);
    }

    @Override
    public void close() throws IOException {
        fileInputStream.close();
    }
}
