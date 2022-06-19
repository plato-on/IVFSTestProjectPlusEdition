package main.java.com.platon.service.impl;

import main.java.com.platon.entity.IVFSInputStream;
import main.java.com.platon.entity.IVFSOutputStream;
import main.java.com.platon.exception.BufferTooLargeException;
import main.java.com.platon.exception.FileNameIsNullException;
import main.java.com.platon.exception.TooManyFilesException;
import main.java.com.platon.service.IVFS;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static main.java.com.platon.constant.CoreConstants.*;

@Slf4j
@EqualsAndHashCode
public class IVFSImpl implements IVFS { //works with integer maxValue 2147483647 and with numbers/english-language-based files

    private volatile File readFile;
    private volatile File writeFile;
    private static volatile byte fileCount;

    @Override
    synchronized public IVFSInputStream openReadOnlyFile(String absolutePath) throws FileNotFoundException {
        log.info(OPENING_FILE);
        isFileNameEmptyOrNull(absolutePath);
        readFile = new File(absolutePath);
        isFileNull(readFile);

        if (fileExistsAlready(readFile) & !isReaderOpenedAlready()) {

            return generateReadEntity(readFile);

        }
        System.out.println("nullptr (is opened or does not exist)");
        return null;
    }


    @Override
    synchronized public IVFSOutputStream openOrCreateWriteOnlyFile(String absolutePath) throws IOException {
        log.info(OPENING_FILE);
        isFileNameEmptyOrNull(absolutePath);
        File file = new File(absolutePath);
        isFileNull(file);

        if (fileExistsAlready(file)) {

            if (!isWriterOpenedAlready()) {
                if (file.setWritable(true, false)) {
                    writeFile = file;
                    return generateWriteEntity(writeFile);
                }
                throw new FileNotFoundException();
            }

            System.out.println("nullptr (is opened in readOnly)");
            return null;
        }
        return createFile(absolutePath);
    }


    @Override
    synchronized public long readDataFromExistingFile(IVFSInputStream ivfsInputStream, char[] buffer, long len) throws IOException {
        log.info(READING_FILE);
        isFileNull(IVFSInputStream.getFile());

        int convertedLen = intFromLongExtractor(len);

        if (convertedLen != -1) {
            byte[] bytesArray = new byte[convertedLen];

            try {
                int count = ivfsInputStream.read(bytesArray, 0, convertedLen);

                for (int i = 0; i < bytesArray.length; i++) { //writing results to the char[] buffer
                    char character = (char) (bytesArray[i] & 0xFF);
                    buffer[i] = character;
                }
                return count;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        throw new BufferTooLargeException();
    }


    @Override
    synchronized public long writeDataToExistingFile(IVFSOutputStream ivfsOutputStream, char[] buffer, long len) throws IOException {
        log.info(WRITING_TO_FILE);
        isFileNull(IVFSOutputStream.getFile());

        int convertedLen = intFromLongExtractor(len);
        if (convertedLen != -1) {
            byte[] bytesArray = new String(buffer).getBytes(StandardCharsets.UTF_8);

            try {
                ivfsOutputStream.getDataOutputStream().write(bytesArray, 0, convertedLen);
                return ivfsOutputStream.getDataOutputStream().size();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new BufferTooLargeException();
    }


    @Override
    public void closeWriteStream(IVFSOutputStream ivfsOutputStream) {
        log.info(CLOSE_ENTITY);
        if (ivfsOutputStream.getClass().equals(IVFSOutputStream.class))
            try {
                ivfsOutputStream.flush();
                ivfsOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void closeReadStream(IVFSInputStream ivfsInputStream) {
        log.info(CLOSE_ENTITY);
        if (ivfsInputStream.getClass().equals(IVFSInputStream.class))
            try {
                ivfsInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    synchronized private IVFSOutputStream createFile(String absolutePath) throws IOException { //creates new file AND generates IVFSOutputStream, returns it
        fileCounter(absolutePath);
        if (fileCount < 10) {
            org.apache.commons.io.FileUtils.touch(new File(absolutePath));

            return generateWriteEntity(writeFile);
        }
        throw new TooManyFilesException("System handles only 10 physical files.");
    }


    synchronized private void isFileNameEmptyOrNull(String filename) {
        if (StringUtils.isEmpty(filename)) {
            log.error(FILENAME_IS_NULL);
            throw new FileNameIsNullException(FILENAME_IS_NULL);
        }
    }


    synchronized private void isFileNull(File file) {
        if (file == null) {
            log.error(FILE_IS_NULL);
            throw new FileNameIsNullException("File is null");
        }
    }

    synchronized private IVFSInputStream generateReadEntity(File readFile) throws FileNotFoundException {
        log.info(GENERATING_READER_ENTITY);

        IVFSInputStream ivfsInputStream;

        ivfsInputStream = new IVFSInputStream(new FileInputStream(readFile));
        IVFSOutputStream.setFile(writeFile);
        return ivfsInputStream;
    }

    synchronized private IVFSOutputStream generateWriteEntity(File writeFile) throws FileNotFoundException {
        log.info(GENERATING_WRITER_ENTITY);

        IVFSOutputStream ivfsOutputStream;

        ivfsOutputStream = new IVFSOutputStream(new FileOutputStream(writeFile, true));
        IVFSOutputStream.setFile(writeFile);
        return ivfsOutputStream;

    }


    synchronized private boolean isReaderOpenedAlready() {
        log.info(CHECK_IF_FILE_OPENED);

        if (IVFSInputStream.getFile() == null)
            return false;

        if (IVFSInputStream.getFile() == null)
            return false;

//        System.out.println(IVFSInputStream.getFile().hashCode()); //uncomment to check hashcode
//        System.out.println(readFile.hashCode());                  //uncomment to check hashcode

        if (IVFSInputStream.getFile().hashCode() == readFile.hashCode()) {
            log.error(FILE_IS_ALREADY_OPENED_IN_CURRENT_MOD);
        }

        return IVFSInputStream.getFile().hashCode() == writeFile.hashCode(); //if the File value of file(Reader/Writer)Entity is the same object with given one
    }


    synchronized private boolean isWriterOpenedAlready() {
        log.info(CHECK_IF_FILE_OPENED);

        if (IVFSOutputStream.getFile() == null)
            return false;

        if (IVFSOutputStream.getFile() == null)
            return false;

//        System.out.println(fileWriterEntity.getFile().hashCode()); //uncomment to check hashcode
//        System.out.println(writeFile.hashCode());                  //uncomment to check hashcode

        if (IVFSOutputStream.getFile().hashCode() == writeFile.hashCode()) {
            log.error(FILE_IS_ALREADY_OPENED_IN_CURRENT_MOD);
        }

        return IVFSOutputStream.getFile().hashCode() == readFile.hashCode(); //if the File value of file(Reader/Writer)Entity is the same object with given one
    }


    synchronized private boolean fileExistsAlready(File file) {
        log.info(CHECK_EXISTENCE);

        return file.exists();
    }


    static synchronized public void fileCounter(String absolutePath) {
        log.info(CHECK_AMOUNT_OF_FILES);
        try (Stream<Path> files = Files.list(Paths.get(absolutePath).getParent())) { //count amount of files into root directory
            fileCount = (byte) files.count();
            //System.out.println("Amount of files: " + fileCount); - uncomment to check filecount meaning
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized private Integer intFromLongExtractor(long theLong) { //returns -1 if theLong is too large to convert correctly OR returns valid integer if long is valid
        log.info(EXTRACTING_LONG);
        return theLong < Integer.MAX_VALUE ? Math.toIntExact(theLong) : -1;
    }

}
