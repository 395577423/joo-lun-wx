package com.joolun.web.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author Owen
 * @date 2021/12/31 11:08 AM
 */
public class FileUtils {

    public static File mutipartFileToFile(MultipartFile file) throws IOException {
        File toFile = null;
        if (!file.equals("") && file.getSize() > 0) {
            InputStream inputStream;
            inputStream = file.getInputStream();

            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(inputStream, toFile);
            inputStream.close();
        }
        return toFile;
    }

    private static void inputStreamToFile(InputStream inputStream, File file) {

        try {
            OutputStream outputStream = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTempFile(File file) {
        if (null != file) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
