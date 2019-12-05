package com.tinyspot.question.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.tinyspot.question.entity.Result;
import com.tinyspot.question.entity.User;
import com.tinyspot.question.service.UserService;
import com.tinyspot.question.util.ResultGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tinyspot
 * @Time 2019/11/21-16:01
 * 用户相关的控制器
 */
@RestController
@RequestMapping("/user")
@PropertySource("classpath:application.properties") //从application.properties文件中读取配置
public class UserController {
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Value("${path.image.dir}")
    private String imageDir;

    @Autowired
    UserService userService;


    /**
     * 获取用户详细信息， member_info.html
     * @param session
     * @return
     */
    @GetMapping("")
    public Result getUserDetail(HttpSession session){
        //先获取用户实体信息
        User user = userService.getUser((Integer) session.getAttribute("userId"));
        //再获取用户发布问卷和参与问卷信息


        Result res = null;
        if (ObjectUtil.isEmpty(user)) { //表明查询失败
            res = ResultGeneratorUtil.genFailResult();
        } else {
            res = ResultGeneratorUtil.genSuccessResult();
            Map<String, Object> data = new HashMap<>();
            data.put("userName", user.getUserName());
            data.put("registerTime", DateUtil.formatDate(user.getRegisterTime())); //转换成yyyy-mm-dd格式

            res.setData(data);
        }
        return res;
    }

    /**
     * 更新用户昵称或密码 member_info.html
     * @param type
     * @param nickName
     * @param prePass
     * @param newPass
     * @param session
     * @return
     */
    @PutMapping("")
    public Result updateUserNickNameOrPassword(@RequestParam("type") int type, @RequestParam(value = "newNick", required = false) String nickName
                                            ,@RequestParam(value = "oldPwd", required = false) String prePass
                                            ,@RequestParam(value = "newPwd1", required = false) String newPass, HttpSession session){
        LOG.debug("用户更新密码或昵称.../user-PUT");
        Result res = new Result();
        if (type == 1) { //修改昵称
            if (ObjectUtil.isEmpty(nickName)) {
                res.setCode(ResultGeneratorUtil.INPUT_IS_NULL);
            } else {
                boolean success = userService.updateNickName(nickName, (Integer)session.getAttribute("userId"));
                if (success) {
                    res.setCode(ResultGeneratorUtil.RESULT_CODE_SUCCESS);
                    res.setData(nickName);
                    session.setAttribute("nickName", nickName); //修改session中的nickName
                }
                else res.setCode(ResultGeneratorUtil.RESULT_CODE_SERVER_ERROR);
            }
        } else if (type == 2){  //type == 2 修改密码
            if (ObjectUtil.isEmpty(prePass) || ObjectUtil.isEmpty(newPass)) {
                res.setCode(ResultGeneratorUtil.INPUT_IS_NULL);
            } else {
                int code = userService.updatePassword(prePass, newPass, (Integer)session.getAttribute("userId"));
                if (code == 1) //修改成功
                    res.setCode(ResultGeneratorUtil.RESULT_CODE_SUCCESS);
                else if (code == 2) //查询数据库出错
                    res.setCode(ResultGeneratorUtil.RESULT_CODE_SERVER_ERROR);
                else { //原始密码错误
                    res.setCode(ResultGeneratorUtil.PREPASS_ERROR);
                    res.setMsg("原始密码错误！");
                }
            }
        }else res.setCode(ResultGeneratorUtil.INPUT_IS_NULL); //type不为1或2
        return res;
    }

    /**
     * 更新头像  member_info.html
     * @param pic
     * @param session
     * @return 621图片过大，622图片类型不符合，623图片存储错误，200操作成功，500数据库更新错误
     */
    @PutMapping("/updateImage")
    public Result updateHeadImage(@RequestParam("pic") MultipartFile pic, HttpSession session){
        LOG.debug("更新图片.../updateImage-PUT");
        LOG.debug(pic.getName());
        String contentType = pic.getContentType();
        LOG.debug(contentType);
        long size = pic.getSize();

        Result res = ResultGeneratorUtil.genSuccessResult();
        //验证图片大小（小于等于2M)
        if (size<=0 || size>2097152) {
            res.setCode(ResultGeneratorUtil.FILE_TOO_BIG);
            return res;
        }
        //验证图片类型(png或jpg)
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            res.setCode(ResultGeneratorUtil.PICTURE_TYPE_ERROR);
            return res;
        }
        //原头像详细地址
        String userId = String.valueOf(session.getAttribute("userId"));
        StringBuilder picPath = new StringBuilder(imageDir);
        picPath.append(userId);
        //删除原来头像并保存新头像
        int code = userService.updateHeadImage(picPath, pic);
        if (code == 0) {
            res.setCode(ResultGeneratorUtil.PICTURE_STORAGE_ERROR);
            return res;
        } else if (code == 2) {
            //修改数据库里头像的名称
            String newName = userId + ".jpg";
            boolean success = userService.updateImgName(newName, Integer.valueOf(userId));
            if (success) {
                res.setCode(ResultGeneratorUtil.RESULT_CODE_SUCCESS);
                res.setData(newName);
                session.setAttribute("imgName", newName);
            } else res.setCode(ResultGeneratorUtil.RESULT_CODE_SERVER_ERROR);
        }
        return res;
    }

    /**
     * 用户登录接口，login.html
     * @param userName
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session){
        LOG.debug("用户登录.../login-GET");

        Result res = null;

        User user = userService.loginVerify(userName, password);
        if (ObjectUtil.isEmpty(user)) {
            res = ResultGeneratorUtil.genFailResult();
        } else { //验证成功
            session.setAttribute("userId", user.getId()); //保存用户的userId
            session.setAttribute("userType", user.getUserType()); //保存用户类型
            session.setAttribute("imgName", user.getImgName()); //保存用户头像名
            session.setAttribute("nickName", user.getNickName()); //保存用户昵称
            res = ResultGeneratorUtil.genSuccessResult();
        }
        return res;
    }


    @GetMapping("/auth")
    public Result auth(HttpSession session){
        LOG.debug("/auth...GET");

        Result result = ResultGeneratorUtil.genSuccessResult();
        Integer userId = (Integer)session.getAttribute("userId");

        //未登录
        if (ObjectUtil.isEmpty(userId)) {
            result.setCode(ResultGeneratorUtil.NEED_LOGIN);
        } else { //已经登录
            result.setCode(ResultGeneratorUtil.ALREADY_LOGGED);
            Map<String, Object> data = new HashMap<>();
            data.put("nickName", session.getAttribute("nickName"));
            data.put("userType", session.getAttribute("userType"));
            data.put("imgName", session.getAttribute("imgName"));
            result.setData(data);
        }
        return result;
    }
}
