package main.java.com.platon;

import lombok.extern.slf4j.Slf4j;
import main.java.com.platon.constant.AbsolutePaths;

import main.java.com.platon.entity.IVFSInputStream;
import main.java.com.platon.service.IVFS;
import main.java.com.platon.service.impl.IVFSImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class Check {

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        IVFS ivfs = new IVFSImpl();

//        // ------OpenReadOnlyFile-------
//        IVFSInputStream file = ivfs.openReadOnlyFile(AbsolutePaths.filePath5);
//        System.out.println(file);
//        //---


//        // ------readDataFromExistingFile-------

//        //---to check if data is read in massive AND to check if it returns amount of read bytes---
//        char[] buffer = new char[28];
//        System.out.println(buffer);
//        int len = 28;
//        FileReaderEntity file = ivfs.openReadOnlyFile(AbsolutePaths.filePath5);
//        System.out.println(ivfs.readDataFromExistingFile(file, buffer, len));
//        System.out.println(buffer);
//        //---

//        //---to watch if FileInputStreams compare before and after---
//        char[] buffer = new char[28];
//        System.out.println(buffer);
//        int len = 28;
//        FileReaderEntity file = ivfs.openReadOnlyFile(AbsolutePaths.filePath3);
//        System.out.println(file);
//        System.out.println(file.getFile());
//        System.out.println(file.getFileInputStream());
//        ivfs.readDataFromExistingFile(file, buffer, len);
//        System.out.println(buffer);
//        System.out.println(file);
//        System.out.println(file.getFile());
//        System.out.println(file.getFileInputStream());
//        //---

//        // ------openOrCreateWriteOnlyFile-------
//        //---to open existing file for several times---
//        while(true) {
//            FileWriterEntity fileWriterEntity = ivfs.openOrCreateWriteOnlyFile(AbsolutePaths.filePath6);
//            System.out.println(fileWriterEntity.getFile()); //outputStreams closes imidiatly so it's null
//            System.out.println(fileWriterEntity.getFileOutputStream());
//            System.out.println(fileWriterEntity.getDataOutputStream());
//        }
//        //---

//        //---to make new file---
//        ivfs.openOrCreateWriteOnlyFile(AbsolutePaths.filePath10);
//        //---

//        //---make more than 10 files---
          //ivfs.openOrCreateWriteOnlyFile(AbsolutePaths.filePath11);
//        //---


//        // ------Validation checks------
//        //---Counter--- (checking amount of files)
//        IVFSImpl.fileCounter(AbsolutePaths.filePath1);
//        //---

//        //---isFileNull---
//        FileReaderEntity file = ivfs.openReadOnlyFile(AbsolutePaths.Null);
//        //---

//        //---isFileNameNull---
//        FileReaderEntity file = ivfs.openReadOnlyFile(AbsolutePaths.fileNameIsNull);
//        //---

//        //---Is(Reader/Writer)OpenedAlready--- !Don't forget to uncomment strings in method IsReadOpenedAlready!
//        while(true) {
//            if(Objects.equals(reader.readLine(), "stop")) { //enter "stop" to stop the loop
//                break;
//            }
//            FileReaderEntity file = ivfs.openReadOnlyFile(AbsolutePaths.filePath4);
//            FileWriterEntity fileWriterEntity = ivfs.openOrCreateWriteOnlyFile(AbsolutePaths.filePath4);
//        }
//        //---


//            System.out.println(ivfs.openReadOnlyFile(AbsolutePaths.filePath1).getFile()); //check if FileReaderEntity gets correct value of File
//            System.out.println(ivfs.openReadOnlyFile(AbsolutePaths.filePath1).getFileInputStream()); //check if FileReaderEntity gets correct value of File
//           System.out.println(ivfs.openOrCreateWriteOnlyFile(AbsolutePaths.filePath2).getFileOutputStream()); //check if FileReaderEntity gets correct value of File
//           System.out.println(ivfs.openOrCreateWriteOnlyFile(AbsolutePaths.filePath2).getDataOutputStream()); //check if FileReaderEntity gets correct value of File//check if FileReaderEntity gets correct value of File






    }
}
