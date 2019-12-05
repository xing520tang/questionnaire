package com.tinyspot.question.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.io.File;

/**
 * @Author tinyspot
 * @Time 2019/11/25-9:23
 */
public class CommonUtils {

    /**
     * 生成一个256位的hash串
     * @param source 源字符串
     * @return
     */
    public static String genSHA_256(String source){
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        String code = sha256.digestHex(source);
        return code;
    }

    /*
    输入图片地址，返回图片是否删除成功。
     */
    public static boolean deletePicture(String path){
        File pic = new File(path);
        return pic.delete();
    }
}
