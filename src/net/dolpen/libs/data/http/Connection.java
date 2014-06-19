package net.dolpen.libs.data.http;


import net.dolpen.libs.data.input.Streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * メソッドチェインでなんでもロードできるすごいやつ
 */
public class Connection {

    private String url;

    private String ref = null;

    private String ua = null;

    private Map<String, String> q;

    private Map<String, String> h;

    private static String ENCODING = "UTF-8";

    private static String UA = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36";

    /**
     * URLだけ指定する
     *
     * @param url URL
     */
    public Connection(String url) {
        q = new HashMap<String, String>();
        h = new HashMap<String, String>();
        userAgent(UA);
        this.url = url;
    }

    /**
     * クエリを一括追加する
     *
     * @param q クエリ
     * @return this
     */
    public Connection query(Map<String, String> q) {
        this.q.putAll(q);
        return this;
    }

    /**
     * パラメータ名と値を指定してクエリを追加する
     *
     * @param key   パラメータ名
     * @param param 値
     * @return this
     */
    public Connection query(String key, String param) {
        this.q.put(key, param);
        return this;
    }

    /**
     * リクエストヘッダを一括追加する
     *
     * @param h ヘッダ
     * @return this
     */
    public Connection header(Map<String, String> h) {
        this.h.putAll(h);
        return this;
    }

    /**
     * パラメータ名と値を指定してリクエストヘッダを追加する
     *
     * @param key   パラメータ名
     * @param param 値
     * @return this
     */
    public Connection header(String key, String param) {
        this.h.put(key, param);
        return this;
    }

    /**
     * リファラを指定する
     *
     * @param referer リファラ
     * @return this
     */
    public Connection referer(String referer) {
        header("Referer", referer);
        return this;
    }

    /**
     * UAを指定する
     *
     * @param ua UA
     * @return this
     */
    public Connection userAgent(String ua) {
        header("User-Agent", ua);
        return this;
    }


    /**
     * GETリクエストして、レスポンスをStringとして返す
     *
     * @return responseText
     * @throws java.io.IOException
     */
    public String getBody() throws IOException {
        String qstr = encodeQuery(q);
        URL u = new URL(url + (qstr != null ? "?" : "") + qstr);
        URLConnection c = u.openConnection();
        for (Map.Entry<String, String> e : h.entrySet())
            c.addRequestProperty(e.getKey(), e.getValue());
        String encoding = c.getContentEncoding();
        if (encoding == null) encoding = ENCODING;
        InputStream in = Streams.ignoreUtf8Bom(c.getInputStream(), encoding);
        return Streams.toString(in, encoding);
    }

    /**
     * POSTリクエストして、レスポンスをStringとして返す
     *
     * @return responseText
     * @throws java.io.IOException
     */
    public String postBody() throws IOException {
        URL u = new URL(url);
        URLConnection c = u.openConnection();
        for (Map.Entry<String, String> e : h.entrySet())
            c.addRequestProperty(e.getKey(), e.getValue());
        c.setDoOutput(true);
        OutputStream os = c.getOutputStream();//POST用のOutputStreamを取得
        PrintStream ps = new PrintStream(os);
        ps.print(encodeQuery(q));//データをPOSTする
        ps.close();
        String encoding = c.getContentEncoding();
        if (encoding == null) encoding = ENCODING;
        InputStream in = Streams.ignoreUtf8Bom(c.getInputStream(), encoding);
        return Streams.toString(in, encoding);
    }

    /**
     * POSTリクエストして、レスポンスをStringとして返す
     *
     * @return responseText
     * @throws java.io.IOException
     */
    public String bulkPostBody(String body) throws IOException {
        URL u = new URL(url);
        URLConnection c = u.openConnection();
        for (Map.Entry<String, String> e : h.entrySet())
            c.addRequestProperty(e.getKey(), e.getValue());
        c.setDoOutput(true);
        OutputStream os = c.getOutputStream();//POST用のOutputStreamを取得
        PrintStream ps = new PrintStream(os);
        ps.print(body);//データをPOSTする
        ps.close();
        String encoding = c.getContentEncoding();
        if (encoding == null) encoding = ENCODING;
        InputStream in = Streams.ignoreUtf8Bom(c.getInputStream(), encoding);
        return Streams.toString(in, encoding);
    }

    /**
     * クエリパラメータをエンコードする
     *
     * @param q パラメータ
     * @return エンコード済みのクエリ文字列
     */
    private static String encodeQuery(Map<String, String> q) {
        if (q == null || q.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> e : q.entrySet()) {
                sb.append(first ? "" : "&");
                first = false;
                sb.append(URLEncoder.encode(e.getKey(), ENCODING))
                    .append("=")
                    .append(URLEncoder.encode(e.getValue(), ENCODING));
            }
        } catch (Exception e) {
            return "";
        }
        return sb.toString();
    }

    /**
     * dry-run query
     *
     * @return query
     * @throws java.io.IOException
     */
    public String getEncodedQuery() throws IOException {
        return encodeQuery(q);
    }
}
