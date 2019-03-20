package com.example.modules.front.dao;

import com.example.modules.front.conn.HdfsConn;
import com.example.modules.front.entity.FileEntity;
import com.example.modules.sys.entity.SysUserEntity;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: lanxinghua
 * Date: 2019/3/20 11:05
 * Desc: hdfs文件操作
 */
@Repository("hdfsDao")
public class HdfsDao {
    private final String basePath = "/disk/";

    /**
     * 获得在hdfs中的目录 /disk/username/1111/1111.pdf
     *
     * @param file
     * @param user
     * @return
     */
    public String formatPathMethod(SysUserEntity user, FileEntity file) {
        return basePath + user.getUsername() + file.getPath();
    }


    /**
     * 上传文件
     *
     * @param inputStream
     * @param file
     * @param user
     */
    public void put(InputStream inputStream, FileEntity file, SysUserEntity user) {
        try {
            String formatPath = formatPathMethod(user, file);
            OutputStream outputStream = HdfsConn.getFileSystem().create(new Path(formatPath), new Progressable() {
                @Override
                public void progress() {
                    //System.out.println("upload OK");
                }
            });
            IOUtils.copyBytes(inputStream, outputStream, 2048, true);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
