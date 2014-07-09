package net.dolpen.libs.data.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
        JPG,
        BMP,
        PNG,
        GIF
    }


    BufferedImage buffer;

    public Images(File imageFile) throws IOException {
        buffer = ImageIO.read(imageFile);
    }

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
        ImageIO.write(buffer, format.name(), dest);
    }

    public void writeTo(File dest) throws IOException {
        writeTo(dest, Format.PNG);
    }
}
