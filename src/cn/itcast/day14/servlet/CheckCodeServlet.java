package cn.itcast.day14.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CheckCodeServlet extends HttpServlet {

    String src = "abcdefghijkmnpqrstuvwxyz23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //一、生成随机字符串（验证码）
        //生成随机数，得到随机数对应src字符串中的字符，拼成验证码

        //创建随机对象
        Random random = new Random();
        //用于保存验证码的字符串缓存
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            //从源字符串中随机取得一个字符
            int r = random.nextInt(src.length());
            //把取得的字符追加到字符缓存中
            sb.append(src.substring(r, r + 1));
        }

        //得到最终的验证码
        String checkCode = sb.toString();
        System.out.println(checkCode);
        //二、用程序画一张图片（100宽，26高），把验证码画到图片上。BufferedImage对象可以理解为画布
        BufferedImage image = new BufferedImage(100, 26, BufferedImage.TYPE_INT_RGB);
        //Graphics对象可以理解为画笔，总体思路，是先设置笔的颜色，然后画想要的图形
        Graphics g = image.getGraphics();
        //首先给图片填充一个底色：
        g.setColor(new Color(210, 210, 255));
        g.fillRect(0, 0, 100, 26);


        for (int i = 0; i < checkCode.length(); i++) {
            //随机一个颜色
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            //生成一个随机字体对象
            g.setFont(new Font("宋体", Font.BOLD, 14 + random.nextInt(5)));
            //在相对固定的坐标下绘制每个字符
            g.drawString(checkCode.charAt(i) + "", 100 / checkCode.length() * i + random.nextInt(5), 12 + random.nextInt(10));


            //以下代码绘制干扰图形

            //随机一个颜色
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            //画线
            g.drawLine(random.nextInt(100), random.nextInt(26), random.nextInt(100), random.nextInt(26));

            //随机一个颜色
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            //画点
            g.fillOval(random.nextInt(100), random.nextInt(26), 3 + random.nextInt(3), 3 + random.nextInt(3));
        }

        //三、使用resp.getOutputStream().输出图片内容到浏览器
        //使用ImageIO工具进行输出
        ImageIO.write(image, "jpeg", resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
