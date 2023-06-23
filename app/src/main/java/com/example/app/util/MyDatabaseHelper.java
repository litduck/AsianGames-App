package com.example.app.util;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDatabaseHelper {
    //导入的sqlite数据库文件名
    private final String DB_NAME = "AsianGames.db";
    private Context context;

    //定义类的方法
    public MyDatabaseHelper(Context context) {
        this.context = context;
    }

    // 复制和加载区域数据库中的数据
    public String CopyDBFile() throws IOException {

        // 在第一次运行应用程序时，加载数据库到data/data/当前包的名称/database/数据库名字
        //获取准确的路径,context.getPackageName()得到包名
        File dir = new File("data/data/" + context.getPackageName() + "/databases");
        //如果文件夹不存在，则创建指定的文件
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
        //文件声明
        File file = new File(dir, DB_NAME);
        //输入流
        InputStream inputStream = null;
        //输出流
        OutputStream outputStream = null;
        //若不存在，通过IO流的方式，将assets目录下的数据库文件，写入到项目模拟手机中，当开启模拟器时，会将数据库文件写入到模拟手机的内存中
        if (!file.exists()) {
            try {
                //创建文件
                file.createNewFile();
                //加载文件
                inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + DB_NAME);
                //输出到文件
                outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len;
                //按字节写入
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }


            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                //关闭资源
                if (outputStream != null) {

                    outputStream.flush();
                    outputStream.close();

                }
                if (inputStream != null) {
                    inputStream.close();
                }

            }

        }

        return file.getPath();
    }

}
