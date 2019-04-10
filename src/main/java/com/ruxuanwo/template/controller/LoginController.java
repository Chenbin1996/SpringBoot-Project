package com.ruxuanwo.template.controller;


import com.ruxuanwo.template.constant.Constant;
import com.ruxuanwo.template.dto.Result;
import com.ruxuanwo.template.enums.ResultEnum;
import com.ruxuanwo.template.redis.CacheAccess;
import com.ruxuanwo.template.service.SysUserService;
import com.ruxuanwo.template.utils.CookieUtil;
import com.ruxuanwo.template.utils.ImageUtil;
import com.ruxuanwo.template.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * 管理用户登录、登出、验证码等controller
 *
 * @author ruxuanwo
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private CacheAccess cacheAccess;
    @Autowired
    private SysUserService userService;
    @Autowired
    private ImageUtil imageUtil;

    /**
     * 跳转登录页面
     *
     * @return
     */
    @GetMapping("/loginUI")
    public String loginUI() {
        return "/login";
    }

    /**
     * 登录接口，登录成功，在本地Cookie中写入token
     *
     * @param loginName   登录名
     * @param passWord    密码
     * @param imageCode   图片验证码
     * @param imageCodeId 图片验证码唯一标识，每次请求的key，由前端生成
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/login")
    public Result login(@RequestParam String loginName,
                        @RequestParam String passWord,
                        @RequestParam String imageCode,
                        @RequestParam String imageCodeId, HttpServletResponse response) throws Exception {
        //验证码校验
        Result result = validateCode(imageCodeId, imageCode, true);
        if (result != null) {
            return result;
        }
        Result overResult = userService.login(loginName, passWord);
        if (overResult.getCode() == ResultEnum.LOGIN_SUCCESS.code()) {
            CookieUtil.writeCookie(response, Constant.HTTP_HEADER_ACCESS_TOKEN, (String) overResult.getData());
        }
        return overResult;
    }

    /**
     * 用户登出接口，删除Cookie中的缓存
     *
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        CookieUtil.removeCookie(response, Constant.HTTP_HEADER_ACCESS_TOKEN);
        return "/login";
    }

    /**
     * 发送短信验证码，待接入厂商，测试阶段
     *
     * @param phone         手机号码
     * @param messageCodeId 短信验证码唯一标识，由前端生成
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/getMessageCode")
    public Result getMessageCode(@RequestParam String phone, @RequestParam String messageCodeId) throws Exception {
        String messageCode = getRandNum();
        cacheAccess.setMessageCode(messageCodeId, messageCode, 2 * 60);
        return ResultUtil.success("短信发送成功", messageCode);
    }

    /**
     * 获取图形验证码
     *
     * @param response
     * @param imageCodeId 图形码唯一标识，由前端生成
     */
    @GetMapping(value = "/getImageCode")
    public void imageCode(HttpServletResponse response, @RequestParam String imageCodeId) {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        try (ServletOutputStream sos = response.getOutputStream(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage image = imageUtil.getImageCode(imageCodeId);
            ImageIO.write(image, "JPEG", bos);
            byte[] buf = bos.toByteArray();
            response.setContentLength(buf.length);
            sos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 效验验证码是否有效，可效验图片验证码和短信验证码
     *
     * @param codeId   前端传入唯一标识
     * @param code     图片验证码或者短信验证码
     * @param classify 分类，true表示图片验证；false表示短信验证
     * @return
     * @throws Exception
     */
    private Result validateCode(String codeId, String code, Boolean classify) throws Exception {
        String returnCode = classify ? cacheAccess.getImageCode(codeId) : cacheAccess.getMessageCode(code);
        if (returnCode == null) {
            return ResultUtil.error("验证码已超时!");
        }
        if (!returnCode.equals(code)) {
            return ResultUtil.error("验证码不正确!");
        }
        return null;
    }

    /**
     * 随机生成6位随机验证码
     *
     * @return 验证码
     */
    private String getRandNum() {
        String randNum = new Random().nextInt(1000000) + "";
        //如果生成的不是6位数随机数则返回该方法继续生成
        int num = 6;
        if (randNum.length() != num) {
            return getRandNum();
        }
        return randNum;
    }
}
