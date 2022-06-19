package main.java.com.platon.entity;

import java.io.*;


public class IVFSOutputStream extends FileOutputStream {

    private static volatile File file;

    private final FileOutputStream fileOutputStream;
    private final DataOutputStream dataOutputStream;

    public IVFSOutputStream(FileOutputStream outputStream) throws FileNotFoundException {
        super(getFile());
        this.fileOutputStream = outputStream;
        this.dataOutputStream = new DataOutputStream(this.fileOutputStream);
    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        IVFSOutputStream.file = file;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        fileOutputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        fileOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        fileOutputStream.flush();
        fileOutputStream.close();
    }

}
