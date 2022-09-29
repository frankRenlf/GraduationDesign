/**
 * Created with IntelliJ IDEA.
 *
 * @author : Frank.Ren
 * @version : 1.0
 * @Project : untitled
 * @Package : PACKAGE_NAME
 * @createTime : 2022/9/29 19:06
 * @Email : sc19lr@leeds.ac.uk
 * @github : https://github.com/frankRenlf
 * @Description :
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageBackgroundDemo {
    public void change(String src, String dest) throws Exception {
        //指定的图片路径
        FileInputStream dir = new FileInputStream(src);
        //新建一个长度为3的数组，负责保存rgb的值
        int[] rgb = new int[3];
        //通过ImageIO.read()方法来返回一个BufferedImage对象，可以对图片像素点进行修改
        BufferedImage bImage = ImageIO.read(dir);
        //获取图片的长宽高
        int width = bImage.getWidth();
        int height = bImage.getHeight();
        int minx = bImage.getMinTileX();
        int miny = bImage.getMinTileY();
        //遍历图片的所有像素点，并对各个像素点进行判断，是否修改
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bImage.getRGB(i, j);
                //获取图片的rgb
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                //进行判断，如果色素点在指定范围内，则进行下一步修改
                if (rgb[0] < 110 && rgb[0] > 50 && rgb[1] < 30 && rgb[1] > 10 && rgb[2] < 50 && rgb[2] > 25) {        //修改像素点，0x007ABB是证件照的蓝色背景色
                    bImage.setRGB(i, j, 0x00ffff);
                }
            }
        }
        //输出照片保存在本地
        FileOutputStream ops;
        try {
            ops = new FileOutputStream(new File(dest));
            //这里写入的“jpg”是照片的格式，根据照片后缀有所不同
            ImageIO.write(bImage, "jpg", ops);
            ops.flush();
            ops.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static ImageBackgroundDemo demo1 = new ImageBackgroundDemo();

    public static void main(String[] args) throws Exception {
        demo1.change("C:\\Users\\11195\\OneDrive - University of Leeds\\Desktop\\Overseas Study\\identify\\test.jpg", "C:\\Users\\11195\\OneDrive - University of Leeds\\Desktop\\Overseas Study\\identify\\id2.jpg");
    }
}
