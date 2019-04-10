package com.ruxuanwo.template.utils;

import com.ruxuanwo.template.redis.CacheAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成图形验证码工具类
 *
 * @author ruxuanwo
 */
@Component
public class ImageUtil {
    @Autowired
    private CacheAccess cacheAccess;

    /**
     * 生成图形码，存入缓存
     *
     * @param imageCodeId 图形码唯一标识，由前端生成
     * @return
     * @throws Exception
     */
    public BufferedImage getImageCode(String imageCodeId) throws Exception {
        // 在内存中创建图象
        int width = 60, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 255; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(4位数字)
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random
                    .nextInt(110)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 13 * i + 6, 24);
        }
        cacheAccess.setImageCode(imageCodeId, sRand, 60);
        // 图象生效
        g.dispose();
        return image;
    }

    /**
     * 返回值参考
     *
     * @param pfc
     * @param pbc
     * @return Color
     * @Title: getRandColor
     * @Description: 给定范围获得随机颜色(这里用一句话描述这个方法的作用)
     * @throw
     */
    private Color getRandColor(int pfc, int pbc) {
        int fc = pfc;
        int bc = pbc;
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
