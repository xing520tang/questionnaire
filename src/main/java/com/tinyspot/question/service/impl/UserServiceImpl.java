package com.tinyspot.question.service.impl;

import com.tinyspot.question.entity.User;
import com.tinyspot.question.mapper.UserMapper;
import com.tinyspot.question.service.UserService;
import com.tinyspot.question.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author tinyspot
 * @Time 2019/11/25-11:36
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    /*
        登录验证，验证用户名和密码是否正确
     */
    @Override
    public User loginVerify(String userName, String password) {
        User user = null;
        try {
            user = userMapper.getUserByUserNameAndPassword(userName, CommonUtils.genSHA_256(password));
        } catch (Exception e){
            LOG.error("UserService.loginVerify根据用户名和密码查询用户出错！", e);
        }
        return user;
    }

    /*
        根据用户id得到一个用户对象，若获取失败则返回null
     */
    @Override
    public User getUser(Integer userId) {
        User user = null;
        try {
            user = userMapper.getUserByUserId(userId);
        } catch (Exception e) {
            LOG.error("UserService.getUser根据用户id查询用户出错！", e);
        }
        return user;
    }

    /*
    更新用户昵称
     */
    @Override
    public boolean updateNickName(String nickName, Integer userId) {
        try {
            userMapper.updateNickName(nickName, userId);
        } catch (Exception e) {
            LOG.error("UserService.updateNickName更新用户昵称出错！", e);
            return false;
        }
        return true;
    }

    /**
     * 更新用户密码
     * @param prePass 原密码
     * @param newPass 新密码
     * @param userId 用户id
     * @return 返回int型状态码，1表示更新成功，2表示查询数据库错误，3表示原密码不正确
     */
    @Override
    public int updatePassword(String prePass, String newPass, Integer userId) {
        //先验证原密码是否正确
        try {
            int cnt = userMapper.getUserByUserIdAndPassword(userId, CommonUtils.genSHA_256(prePass));
            if (cnt == 0) //为查询到相应用户
                return 3;
        } catch (Exception e) {
            LOG.error("UserService.updatePassword验证原密码时错误！", e);
            return 2;
        }
        //再修改密码
        try {
            userMapper.updatePassword(userId, CommonUtils.genSHA_256(newPass));
        } catch (Exception e) {
            LOG.error("userService.updatePassword更新密码时出现错误", e);
            return 2;
        }
        return 1;
    }

    /**
     * 删除原头像，保存新的头像
     * @param picPath
     * @param pic
     * @return 返回3个状态码，0表示更新失败，1表示数据库不需要更新，2表示数据库需要更新
     */
    @Override
    public int updateHeadImage(StringBuilder picPath, MultipartFile pic) {
        int code = 0; //表示更新失败
        //删除旧头像
        if (CommonUtils.deletePicture(picPath + ".jpg"))
            code = 1; //表示已经不是默认头像，所以数据库中的图片名不用改
        else code = 2; //表示头像还是默认头像，所以数据库中的图片名需要改
        //CommonUtils.deletePicture(picPath + ".png");
        //新头像路径及名称,图片统一保存为.jpg格式
        picPath = picPath.append(".jpg");
        //保存图片
        File file = new File(picPath.toString());
        try {
            pic.transferTo(file);
        } catch (IOException e) {
            LOG.error("UserService.updateHeadImage保存头像出错！", e);
            return code;
        }
        return code;
    }

    @Override
    public boolean updateImgName(String s, Integer userId) {
        try {
            userMapper.updateImgName(s, userId);
        } catch (Exception e) {
            LOG.error("UserService.updateImgName数据库更新头像名称失败", e);
            return false;
        }
        return true;
    }
}
