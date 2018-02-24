import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Random;

/**
 * Github头像生成器
 */
public class GithubAvatarGenerator {
    private static final int GITHUB_AVATAR_ROWS = 420;
    private static final int GITHUB_AVATAR_COLS = 420;
    // 围边使用的灰色
    private static final int [] COLOR_GREY_BGR = new int[] {
            230, 230, 230
    };
    // 选出一些大概会比较好看的颜色池用于生成
    private static final int [][] COLOR_POOL_RGB = new int[][] {
            {170, 205, 102},
            {159, 255, 84},
            {209, 206, 0},
            {255, 255, 0},
            {47, 107, 85},
            {47, 255, 173},
            {0, 173, 205},
            {8, 101, 139},
            {180, 180, 238},
            {106, 106, 255},
            {155, 211, 255},
            {204, 50, 153},
            {101, 119, 139}
    };
    // 外围宽度
    private static final int GITHUB_AVATAR_FRAME_WIDTH = 35;
    // Block宽度
    private static final int GITHUB_AVATAR_BLOCK_WIDTH = 70;
    // Vertex 大小
    private static final int GITHUB_AVATAR_VERTEX_WIDTH = 5;

    /**
     * 获取一个 5x5 的随机填充对称矩阵
     * @return 5x5 随机填充对称矩阵
     */
    private boolean [][] getGithubAvatarVertex() {
        // 新建矩阵
        boolean [][] vertex = new boolean[5][5];

        // 先随机填充中间一条
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (random.nextBoolean()) {
                vertex[i][2] = true;
            }
        }

        // 随机填充半边
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                if (random.nextBoolean()) {
                    vertex[i][j] = true;
                }
            }
        }

        // 将填充的半边对称复制到另外半边
        for (int i = 0; i < 5; i++) {
            for (int j = 3; j < 5; j++) {
                vertex[i][j] = vertex[i][4 - j];
            }
        }

        return vertex;
    }

    /**
     * 获取一个随机的 github 头像 BGR 信息
     * @return 图像 BGR 信息
     */
    private byte [][][] getGithubAvatarRGBData() {
        // 通道
        int channels = 3;

        // BGR 信息
        byte [][][] bgrData = new byte[GITHUB_AVATAR_ROWS][GITHUB_AVATAR_COLS][channels];

        // 获取一个随机的颜色索引
        Random random = new Random();
        int randomIndex = random.nextInt() % COLOR_POOL_RGB.length;
        if (randomIndex < 0) {
            randomIndex = 0 - randomIndex;
        }
        // 用索引获取一个随机的颜色
        int [] randomBGR = new int[3];
        System.arraycopy(COLOR_POOL_RGB[randomIndex], 0, randomBGR, 0, 3);

        // 先将外围一圈 35px 填充成灰色
        // top
        for (int i = 0; i < GITHUB_AVATAR_FRAME_WIDTH; i++) {
            for (int j = 0; j < GITHUB_AVATAR_COLS; j++) {
                for (int k = 0; k < channels; k++) {
                    bgrData[i][j][k] = (byte) COLOR_GREY_BGR[k];
                }
            }
        }
        // bottom
        for (int i = GITHUB_AVATAR_COLS - 1; i > GITHUB_AVATAR_COLS - GITHUB_AVATAR_FRAME_WIDTH - 1; i--) {
            for (int j = 0; j < GITHUB_AVATAR_COLS; j++) {
                for (int k = 0; k < channels; k++) {
                    bgrData[i][j][k] = (byte) COLOR_GREY_BGR[k];
                }
            }
        }
        // left
        for (int i = 0; i < GITHUB_AVATAR_COLS; i++) {
            for (int j = 0; j <GITHUB_AVATAR_FRAME_WIDTH; j++) {
                for (int k = 0; k < channels; k++) {
                    bgrData[i][j][k] = (byte) COLOR_GREY_BGR[k];
                }
            }
        }
        // right
        for (int i = 0; i < GITHUB_AVATAR_COLS; i++) {
            for (int j = GITHUB_AVATAR_ROWS - 1; j > GITHUB_AVATAR_ROWS - GITHUB_AVATAR_FRAME_WIDTH - 1; j--) {
                for (int k = 0; k < channels; k++) {
                    bgrData[i][j][k] = (byte) COLOR_GREY_BGR[k];
                }
            }
        }
        // 将中间 5x5 的范围按照矩阵信息填充
        boolean [][] vertex = getGithubAvatarVertex();
        for (int i = 0; i < GITHUB_AVATAR_VERTEX_WIDTH; i++) {
            for (int j = 0; j < GITHUB_AVATAR_VERTEX_WIDTH; j++) {
                if (vertex[i][j]) {
                    for (int m = GITHUB_AVATAR_FRAME_WIDTH + i * GITHUB_AVATAR_BLOCK_WIDTH;
                         m < GITHUB_AVATAR_FRAME_WIDTH + i * GITHUB_AVATAR_BLOCK_WIDTH + GITHUB_AVATAR_BLOCK_WIDTH;
                         m++) {
                        for (int n = GITHUB_AVATAR_FRAME_WIDTH + j * GITHUB_AVATAR_BLOCK_WIDTH;
                             n < GITHUB_AVATAR_FRAME_WIDTH + j * GITHUB_AVATAR_BLOCK_WIDTH + GITHUB_AVATAR_BLOCK_WIDTH;
                             n++) {
                            for (int k = 0; k < channels; k++) {
                                bgrData[m][n][k] = (byte) randomBGR[k];
                            }
                        }
                    }
                } else {
                    for (int m = GITHUB_AVATAR_FRAME_WIDTH + i * GITHUB_AVATAR_BLOCK_WIDTH;
                         m < GITHUB_AVATAR_FRAME_WIDTH + i * GITHUB_AVATAR_BLOCK_WIDTH + GITHUB_AVATAR_BLOCK_WIDTH;
                         m++) {
                        for (int n = GITHUB_AVATAR_FRAME_WIDTH + j * GITHUB_AVATAR_BLOCK_WIDTH;
                             n < GITHUB_AVATAR_FRAME_WIDTH + j * GITHUB_AVATAR_BLOCK_WIDTH + GITHUB_AVATAR_BLOCK_WIDTH;
                             n++) {
                            for (int k = 0; k < channels; k++) {
                                bgrData[m][n][k] = (byte) COLOR_GREY_BGR[k];
                            }
                        }
                    }
                }
            }
        }

        // 返回 BGR 信息
        return bgrData;
    }

    /**
     * 获取一个随机的头像
     * @param fileName 文件名
     */
    public void getARandomAvatar(String fileName) {
        // 新建一个 img 文件
        Mat img = new Mat(
                GITHUB_AVATAR_ROWS,
                GITHUB_AVATAR_COLS,
                // RGB 通道
                CvType.CV_8UC3
        );

        // 长
        int width = img.cols();
        // 宽
        int height = img.height();
        // 通道
        int channels = img.channels();

        // 图像中所有像素加通道的储存数据
        int [][][] data = new int[height][width][channels];

        // 获取数据头像 BGR 信息
        byte [][][] randomAvatarBGR = getGithubAvatarRGBData();

        // 将生成的 github 头像信息复制到图片中
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                img.put(i, j, randomAvatarBGR[i][j]);
            }
        }

        // 保存图片
        Imgcodecs.imwrite(fileName, img);
    }
}
