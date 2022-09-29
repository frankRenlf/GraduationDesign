/**
 * Created with IntelliJ IDEA.
 *
 * @author : Frank.Ren
 * @version : 1.0
 * @Project : untitled
 * @Package : PACKAGE_NAME
 * @createTime : 2022/9/29 19:39
 * @Email : sc19lr@leeds.ac.uk
 * @github : https://github.com/frankRenlf
 * @Description :
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

public class demo3 {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        /*
         * 待处理的图片
         * */
        File dir = new File("C:\\Users\\11195\\OneDrive - University of Leeds\\Desktop\\Overseas Study\\identify");
        /*
         * 列出目录中的图片，得到数组
         * */
        File[] files = dir.listFiles();
        /*
         * 遍历数组
         * */
        for (int x = 0; x < files.length; x++) {
            /*
             * 定义一个数组，存放RGB值
             * */
            int[] rgb = new int[3];
            /*
             * byte转换BuffereImage
             * */
            BufferedImage bImage = null;
            bImage = ImageIO.read(files[x]);

            int width = bImage.getWidth();
            int height = bImage.getHeight();
            int minx = bImage.getMinTileX();
            int miny = bImage.getMinTileY();
            System.out.println("正在处理..." + files[x].getName());

            /*
             * 遍历像素点，判断是否更换颜色
             * */
            for (int i = minx; i < width; i++) {
                for (int j = miny; j < height; j++) {
                    /*
                     * 换色
                     * */
                    int pixel = bImage.getRGB(i, j);
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);

                    //红--> 蓝
                    //此处是判断RGB 的值是否是你想要替换的值
                    if (rgb[0] < 150 && rgb[0] > 100
                            && rgb[1] < 50
                            && rgb[2] < 50 ) {
                        //bImage.setRGB(i, j, 0x438EDB);
                        //替换成 蓝色
                        bImage.setRGB(i, j, 0xFFB6C1);
                    }
                }
            }
            System.out.println("\t处理完毕：" + files[x].getName());
            System.out.println();
            /*
             * 输出
             * */
            FileOutputStream ops;
            try {
                ops = new FileOutputStream(new File("C:\\Users\\11195\\OneDrive - University of Leeds\\Desktop\\Overseas Study\\identify\\" + x + ".jpg"));
                ImageIO.write(bImage, "jpg", ops);
                ops.flush();
                ops.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}


