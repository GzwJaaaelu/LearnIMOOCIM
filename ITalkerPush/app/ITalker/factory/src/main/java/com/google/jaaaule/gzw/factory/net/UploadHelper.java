package com.google.jaaaule.gzw.factory.net;

import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.jaaaule.gzw.factory.Factory;
import com.google.jaaaule.gzw.utils.HashUtil;

import java.io.File;
import java.util.Date;

/**
 * 上传工具类，用于上传到任意文件到阿里 OSS
 * Created by admin on 2017/6/14.
 */

public class UploadHelper {
    //  与存储区域有关系
    private static final String END_POINT = "http://oss-cn-hongkong.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIWXEYqWOZGxNn";
    private static final String ACCESS_KEY_SECRET = "phDfMSuoPh3mX35nkftnx3AmAendr3";
    //  上传的仓库名
    private static final String BUCKET_NAME = "mu-chat";
    private static final String TAG = UploadHelper.class.getSimpleName();

    private static OSS getClient() {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider
                = new OSSPlainTextAKSKCredentialProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        OSS oss = new OSSClient(Factory.getApplicationContext(), END_POINT, credentialProvider);
        return oss;
    }

    /**
     * 上传的最终方
     *
     * @param objKey 上传上去后，在服务器上的独立 Key
     * @param path   需要上传的文件的路径
     * @return 返回存储的地址
     */
    private static String upload(String objKey, String path) {
        //  构造上传请求
        PutObjectRequest put = new PutObjectRequest(BUCKET_NAME, objKey, path);
        try {
            //  同步上传
            OSS client = getClient();
            //  开始上传
            PutObjectResult result = client.putObject(put);
            //  得到一个外网可访问的地址
            String url = client.presignPublicObjectURL(BUCKET_NAME, objKey);
            Log.d(TAG, url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            //  如果异常返回空
            return null;
        }
    }

    /**
     * 上传普通图片
     *
     * @param path 图片本地地址
     * @return 图片外网访问地址
     */
    public static String uploadImage(String path) {
        String key = getImageObjKey(path);
        return upload(key, path);
    }

    /**
     * 上传头像
     *
     * @param path 图片本地地址
     * @return 图片外网访问地址
     */
    public static String uploadPortrait(String path) {
        String key = getPortraitObjKey(path);
        return upload(key, path);
    }

    /**
     * 上传音频
     *
     * @param path 图片本地地址
     * @return 图片外网访问地址
     */
    public static String uploadAudio(String path) {
        String key = getAudioObjKey(path);
        return upload(key, path);
    }

    /**
     * 分月存储，避免一个文件夹内容太多
     *
     * @return
     */
    private static String getDateStr() {
        return DateFormat.format("yyyyMM", new Date()).toString();
    }

    private static String getImageObjKey(String path) {
        String fileMD5 = HashUtil.getMD5String(new File(path));
        String dateStr = getDateStr();
        return String.format("image/%s/%s.jpg", dateStr, fileMD5);
    }

    private static String getPortraitObjKey(String path) {
        String fileMD5 = HashUtil.getMD5String(new File(path));
        String dateStr = getDateStr();
        return String.format("portrait/%s/%s.jpg", dateStr, fileMD5);
    }

    private static String getAudioObjKey(String path) {
        String fileMD5 = HashUtil.getMD5String(new File(path));
        String dateStr = getDateStr();
        return String.format("audio/%s/%s.mp3", dateStr, fileMD5);
    }
}
