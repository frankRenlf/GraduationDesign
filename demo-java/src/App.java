import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Frank.Ren
 * @version : 1.0
 * @Project : untitled
 * @Package : PACKAGE_NAME
 * @createTime : 2022/9/29 18:58
 * @Email : sc19lr@leeds.ac.uk
 * @github : https://github.com/frankRenlf
 * @Description :
 */


//public class App {
//
//    // 比较邪乎了，为啥是30，不是20，其实20也可以，就是一个优化参数
//    private static final int critical = 30;
//
//
//    /***
//     * 处理图片背景色
//     * @param path 原图地址
//     * @param targetRgb 目标颜色RGB值 16进制颜色码
//     * @param isNetWork 原图是否为网络图片地址
//     * @return
//     */
//    public static BufferedImage handleBufferImageBackgroundRGB(String path, int targetRgb, boolean isNetWork) throws Exception {
//        File file;
//        if (isNetWork) {
//            //处理网络图片，先将图片下载到本地（上传的头像）
//            file = FileUtils.downloadNetWorkFile(path);
//        } else {
//            file = new File(path);
//        }
//        /**
//         * 用来处理图片的缓冲流
//         */
//        BufferedImage bi = null;
//        try {
//            /**
//             * 用ImageIO将图片读入到缓冲中
//             */
//            bi = ImageIO.read(file);
//        } catch (Exception e) {
//            log.error("图像加载内存失败", e);
//        }
//
//        /**
//         * 得到图片的长宽
//         */
//        int width = bi.getWidth();
//        int height = bi.getHeight();
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        /**
//         * 获取左上角颜色，默认左上角像素块颜色为背景色
//         */
//        int pixel = bi.getRGB(critical, critical);
//        log.info("图片名称:{}, targetRgb:{}, width:{}, height:{}, pixel:{}",
//                file.getName(), targetRgb, width, height, pixel);
//
//        /**
//         * 这里是遍历图片的像素，因为要处理图片的背色，所以要把指定像素上的颜色换成目标颜色
//         * 这里 是一个二层循环，遍历长和宽上的每个像素
//         */
//        Graphics g = image.getGraphics();
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                /**
//                 * 得到指定像素（i,j)上的RGB值，
//                 */
//                int nowPixel = bi.getRGB(x, y);
//                /**
//                 * 进行换色操作，我这里是要把蓝底换成白底，那么就判断图片中rgb值是否在蓝色范围的像素
//                 */
//                // 核心代码：但是这样会有误差，还需要优化边缘、人像边框
//                int p = pixel == nowPixel ? targetRgb : nowPixel;
//                g.setColor(new Color(p));
//                g.fillRect(x, y, 1, 1);
//            }
//        }
//        log.info("处理完毕：{}", file.getName());
//        return image;
//    }
//
//
//}
