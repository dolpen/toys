package net.dolpen.libs.data.input;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 */
public class Streams {
    /**
     * InputStream -> String の変換
     *
     * @param inputStream inputStream
     * @param charset     文字コード
     * @return 文字列
     * @throws java.io.IOException
     */
    public static String toString(InputStream inputStream, String charset) throws IOException {
        InputStreamReader reader = new InputStreamReader(ignoreUtf8Bom(inputStream,charset), charset);
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[1024];
        int numRead;
        while (0 <= (numRead = reader.read(buf))) {
            builder.append(buf, 0, numRead);
        }
        return builder.toString();
    }

    /**
     * BOM付きUTF-8のストリームを無視して使えるストリームにラップする
     *
     * @param is      inputStream
     * @param charset 文字コード
     * @return ラップ済みのimputStream
     * @throws IOException
     */
    public static InputStream ignoreUtf8Bom(InputStream is, String charset) throws IOException {
        if (!charset.toUpperCase().equals("UTF-8")) return is;
        if (!is.markSupported()) {
            is = new BufferedInputStream(is);
        }
        is.mark(3);
        if (is.available() >= 3) {
            byte b[] = {0, 0, 0};
            is.read(b, 0, 3);
            if (b[0] != (byte) 0xEF || b[1] != (byte) 0xBB || b[2] != (byte) 0xBF) {
                is.reset();
            }
        }
        return is;
    }
}
