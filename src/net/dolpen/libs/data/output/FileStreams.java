package net.dolpen.libs.data.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */
public class FileStreams {

    /**
     * 文字列をファイルに変換
     *
     * @param file   対象
     * @param output 内容
     * @throws IOException
     */
    public static void OutputString(File file, String output) throws IOException {
        file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(output.getBytes());
        fos.close();
    }

}
