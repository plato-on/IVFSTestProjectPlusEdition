package main.java.com.platon.service;

import main.java.com.platon.entity.IVFSInputStream;
import main.java.com.platon.entity.IVFSOutputStream;

import java.io.IOException;

public interface IVFS {

    IVFSInputStream openReadOnlyFile(String absolutePath) throws IOException;

    IVFSOutputStream openOrCreateWriteOnlyFile(String absolutePath) throws IOException;

    long readDataFromExistingFile(IVFSInputStream ivfsInputStream, char[] buffer, long len) throws IOException;

    long writeDataToExistingFile(IVFSOutputStream ivfsOutputStream, char[] buffer, long len) throws IOException;

    void closeWriteStream(IVFSOutputStream ivfsOutputStream);

    void closeReadStream(IVFSInputStream ivfsInputStream);
}
