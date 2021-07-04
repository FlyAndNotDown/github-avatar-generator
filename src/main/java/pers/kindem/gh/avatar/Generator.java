package pers.kindem.gh.avatar;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Generator {
    private static class Constants {
        private static final int BLOCK_ROW = 5;
        private static final int BLOCK_ROW_HALF = BLOCK_ROW / 2 + 1;
        private static final int BLOCK_WIDTH = 70;
        private static final int REMAINS_WIDTH = 35;
        private static final int IMAGE_WIDTH = REMAINS_WIDTH * 2 + BLOCK_WIDTH * BLOCK_ROW;
        private static final int IMAGE_HEIGHT = REMAINS_WIDTH * 2 + BLOCK_WIDTH * BLOCK_ROW;
        private static final Color BACKGROUND_COLOR = new Color(230, 230, 230);
    }

    public static class AvatarInfo {
        private final Color color;
        private final boolean[] blocks;

        public AvatarInfo(Color color) {
            this.color = color;
            this.blocks = new boolean[Constants.BLOCK_ROW * Constants.BLOCK_ROW];
        }

        public void setBlockValue(int index, boolean value) {
            this.blocks[index] = value;
        }

        public boolean getBlockValue(int index) {
            return blocks[index];
        }

        public Color getColor() {
            return color;
        }
    }

    private final String seed;
    private int count = 0;

    public Generator(String seed) {
        this.seed = seed;
    }

    public BufferedImage nextAvatar() {
        AvatarInfo avatarInfo = nextAvatarInfo();
        BufferedImage bufferedImage = new BufferedImage(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < bufferedImage.getHeight(); i++) {
            for (int j = 0; j < bufferedImage.getWidth(); j++) {
                bufferedImage.setRGB(i, j, Constants.BACKGROUND_COLOR.getRGB());
            }
        }

        int count = 0;
        for (int i = 0; i < Constants.BLOCK_ROW_HALF; i++) {
            for (int j = 0; j < Constants.BLOCK_ROW; j++) {
                if (!avatarInfo.getBlockValue(count++)) {
                    continue;
                }
                fillImageBlock(bufferedImage, i, j, avatarInfo.getColor());
                fillImageBlock(bufferedImage, Constants.BLOCK_ROW - 1 - i, j, avatarInfo.getColor());
            }
        }

        return bufferedImage;
    }

    private static void fillImageBlock(BufferedImage bufferedImage, int row, int col, Color color) {
        int pixelRowStart = Constants.REMAINS_WIDTH + row * Constants.BLOCK_WIDTH;
        int pixelColStart = Constants.REMAINS_WIDTH + col * Constants.BLOCK_WIDTH;
        for (int i = pixelRowStart; i < pixelRowStart + Constants.BLOCK_WIDTH; i++) {
            for (int j = pixelColStart; j <pixelColStart + Constants.BLOCK_WIDTH; j++) {
                bufferedImage.setRGB(i, j, color.getRGB());
            }
        }
    }

    private AvatarInfo nextAvatarInfo() {
        byte[] hash = nextHash();

        // 3 byte for color, 15 byte for block
        int[] info = new int[18];
        for (int i = 0; i < hash.length; i++) {
            int index = i % 18;
            info[index] = (info[index] + (hash[i] + 128)) % 256;
        }

        AvatarInfo avatarInfo = new AvatarInfo(new Color(info[0], info[1], info[2]));
        for (int i = 3; i < 18; i++) {
            avatarInfo.setBlockValue(i, info[i] > 127);
        }
        return avatarInfo;
    }

    private byte[] nextHash() {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ignored) {}
        if (messageDigest == null) {
            return new byte[0];
        }
        messageDigest.update((seed + count++).getBytes(StandardCharsets.UTF_8));
        return messageDigest.digest();
    }
}
