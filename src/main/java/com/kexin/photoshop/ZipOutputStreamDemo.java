package com.kexin.photoshop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author Pasier
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ZipOutputStreamDemo {
    public static void main(String[] args) throws IOException {
        // 要被压缩的文件夹
        String fileName1 = "D:" + File.separator + "javaIo" + File.separator + "hello";
        File file = new File(fileName1);
        String zipFileName = "d:" + File.separator + "javaIo" + File.separator + file.getName()+".zip";
        File zipFile = new File(zipFileName);
        
        InputStream input = null;
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        // zip的名称为
        zipOut.setComment(file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; ++i) {
                input = new FileInputStream(files[i]);
                zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
                int temp = 0;
                while ((temp = input.read()) != -1) {
                    zipOut.write(temp);
                }
                input.close();
            }
        }
        zipOut.close();
    }

}