package com.joolun.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.joolun.common.utils.StringUtils;
import com.joolun.common.utils.http.HttpUtils;
import com.joolun.weixin.constant.ConfigConstant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Owen
 * 小程序工具类
 */
public class WxMaUtil {

    private static final String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String PHONEURL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";
    public static final String WXACODEURL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";

    private static final String APPID = "wxa8f0475c8754393c";
    private static final String APPSECRET = "c9e74f9a53620b2eb4008979affdcc57";

    /**
     * 通过request获取appId
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getAppId(HttpServletRequest request) {
        return request.getHeader(ConfigConstant.HEADER_APP_ID);
    }

    public static String getToken() {
        String param = "grant_type=client_credential" + "&appid=" + APPID + "&secret=" + APPSECRET;
        String result = HttpUtils.sendGet(TOKENURL, param);
        JSONObject parse = (JSONObject) JSON.parse(result);
        return (String) parse.get("access_token");
    }

    public static String getPhone(String code) throws IOException {

        String token = getToken();
        String url = PHONEURL + token;
        Map<String, String> param = new HashMap<>();
        param.put("code", code);
        String json = JSONObject.toJSON(param).toString();
        String result = HttpUtils.sendPost(url, json);
        System.out.println(result);

        JSONObject obj = JSON.parseObject(result);
        Integer errcode = (Integer) obj.get("errcode");
        if (null != errcode && 0 == errcode) {
            JSONObject phoneInfo = (JSONObject) obj.get("phone_info");
            String purePhoneNumber = (String) phoneInfo.get("purePhoneNumber");
            return purePhoneNumber;
        }
        return null;
    }

}
