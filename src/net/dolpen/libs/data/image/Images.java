package net.dolpen.libs.data.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 画像操作系ライブラリ
 */
public class Images {

    // リサイズ用情報
    private static class ResizeInfo {
        int left;

        int top;

        int width;

        int height;

        int frameWidth;

        int frameHeight;

        public ResizeInfo(int left, int top, int width, int height, int frameWidth, int frameHeight) {
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
        }

    }

    public static enum ResizeStrategy {
        FORCE(true) {
            @Override
            public ResizeInfo getResizeInfo(int width, int height, int afterWidth, int afterHeight) {
                return new ResizeInfo(0, 0, afterWidth, afterHeight, afterWidth, afterHeight);
            }
        }, // アスペクト比を維持しない
        SHORTSIDE(false) {
            @Override
            public ResizeInfo getResizeInfo(int width, int height, int afterWidth, int afterHeight) {
                boolean sidelong = width * afterHeight > width * afterWidth;
                double ratio = sidelong ? (double) afterWidth / width : (double) afterHeight / height;
                int transWidth = (int) (width * ratio);
                int transHeight = (int) (height * ratio);
                return new ResizeInfo(0, 0, transWidth, transHeight, transWidth, transHeight);
            }
        }, // 短辺に合わせる(長辺側がはみ出す)
        LONGSIDE(false) {
            @Override
            public ResizeInfo getResizeInfo(int width, int height, int afterWidth, int afterHeight) {
                boolean sidelong = width * afterHeight > width * afterWidth;
                double ratio = sidelong ? (double) afterHeight / height : (double) afterWidth / width;
                int transWidth = (int) (width * ratio);
                int transHeight = (int) (height * ratio);
                return new ResizeInfo(0, 0, transWidth, transHeight, transWidth, transHeight);
            }
        }, // 長辺に合わせる(短辺側が削れる)
        CUT(true) {
            @Override
            public ResizeInfo getResizeInfo(int width, int height, int afterWidth, int afterHeight) {
                boolean sidelong = width * afterHeight > width * afterWidth;
                double ratio = sidelong ? (double) afterWidth / width : (double) afterHeight / height;
                int transWidth = (int) (width * ratio);
                int transHeight = (int) (height * ratio);
                int transLeft = (afterWidth - transWidth) / 2;
                int transTop = (afterHeight - transHeight) / 2;
                return new ResizeInfo(transLeft, transTop, transWidth, transHeight, afterWidth, afterHeight);
            }
        },  // 長辺側のはみ出しを切り取る
        FIT(true) {
            @Override
            public ResizeInfo getResizeInfo(int width, int height, int afterWidth, int afterHeight) {
                boolean sidelong = width * afterHeight > width * afterWidth;
                double ratio = sidelong ? (double) afterHeight / height : (double) afterWidth / width;
                int transWidth = (int) (width * ratio);
                int transHeight = (int) (height * ratio);
                int transLeft = (afterWidth - transWidth) / 2;
                int transTop = (afterHeight - transHeight) / 2;
                return new ResizeInfo(transLeft, transTop, transWidth, transHeight, afterWidth, afterHeight);
            }
        };// 短辺の不足領域を埋める(領域に収める)

        private boolean forceSizing;

        private ResizeStrategy(boolean forceSizing) {
            this.forceSizing = forceSizing;
        }

        public ResizeInfo getResizeInfo(int width, int height, int afterWidth, int afterHeight) {
            return null;
        }
    }

    public static enum Format {
        JPG(true, 0xffd8000000000000L),
        BMP(true, 0x424D000000000000L),
        PNG(true, 0x89504E470D0A1A0AL),
        GIF(true, 0x4749460000000000L),
        UNKNOWN(false, 0x0000000000000000L);

        private boolean support;

        private long mask;

        private Format(boolean support, long mask) {
            this.support = support;
            this.mask = mask;
        }

        public boolean isSupported() {
            return support;
        }

        public long getMask() {
            return mask;
        }

    }


    private BufferedImage buffer;

    private Format format;


    public Images resize(int width, int height, ResizeStrategy strategy) {
        ResizeInfo info = strategy.getResizeInfo(buffer.getWidth(), buffer.getHeight(), width, height);
        BufferedImage newImage = new BufferedImage(info.frameWidth, info.frameHeight, buffer.getType());
        Graphics g = newImage.createGraphics();
        g.drawImage(buffer, info.left, info.top, info.width, info.height, null);
        g.dispose();
        buffer = newImage;
        return this;
    }


    public Images alpha(int alpha) {
        if (alpha > 255 || alpha < 0) return this;
        int mask = alpha << 24;
        int type = buffer.getTransparency() == Transparency.TRANSLUCENT ? buffer.getType() : BufferedImage.TYPE_INT_ARGB;
        int width = buffer.getWidth();
        int height = buffer.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, type);
        // set alpha channel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newImage.setRGB(x, y, buffer.getRGB(x, y) & 0x00ffffff + mask);
            }
        }
        buffer = newImage;
        return this;
    }

    public void writeTo(File dest, Format format) throws IOException {
        if (format.isSupported()) {
            ImageIO.write(buffer, format.name(), dest);
        } else {
            throw new IllegalArgumentException("指定に使えないパラメータです");
        }
    }

    public void writeTo(File dest) throws IOException {
        writeTo(dest, Format.PNG);
    }

    public Format getFormat() {
        return format;
    }


    public static Images of(File imageFile) throws IOException {
        Images resp = new Images();
        resp.format = getFormatFromFile(imageFile);
        resp.buffer = ImageIO.read(imageFile);
        return resp;
    }

    private static Format getFormatFromFile(File file) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            is.mark(8);
            byte[] b = new byte[8];
            int r = is.read(b, 0, 8);
            if (r != 8) return Format.UNKNOWN;
            long p = 0x00L;
            for (int k = 0; k < 8; k++)
                p |= ((long) b[k] & 0xffL) << (8 * (7 - k));
            for (Format f : Format.values()) {
                long m = f.getMask();
                if ((p & m) == m) return f;
            }
            return Format.UNKNOWN;
        } catch (IOException e) {
            e.printStackTrace();
            return Format.UNKNOWN;
        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException e) {
            }
        }
    }
}
