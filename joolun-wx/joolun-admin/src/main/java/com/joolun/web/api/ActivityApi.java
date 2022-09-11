package com.joolun.web.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.utils.http.HttpUtils;
import com.joolun.mall.entity.Activity;
import com.joolun.mall.entity.ActivityPriceCase;
import com.joolun.mall.service.ActivityPriceCaseService;
import com.joolun.mall.service.IActivityOrderInfoService;
import com.joolun.mall.service.IActivityService;
import com.joolun.weixin.utils.WxMaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-19
 */
@Slf4j
@RestController
@RequestMapping("/weixin/api/activity")
public class ActivityApi extends BaseController {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private ActivityPriceCaseService activityPriceCaseService;

    @Autowired
    private IActivityOrderInfoService activityOrderInfoService;

    /**
     * 查询社会活动列表
     */
    @PostMapping("/list")
    public AjaxResult list(Page page, Activity activity) {
        activity.setPublished(true);
        page = activityService.page(page, Wrappers.lambdaQuery(activity));
        return AjaxResult.success(page);
    }


    /**
     * 查询课程关联的社会活动列表
     */
    @GetMapping("/listByCourseId")
    public AjaxResult list(Long courseId) {
        List<Activity> list = activityService.listByCourseId(courseId);
        return AjaxResult.success(list);
    }

    /**
     * 根据id查询活动详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable("id") Long id) {
        return AjaxResult.success(activityService.getById(id));
    }

    /**
     * 根据id查询活动价格信息
     *
     * @param activityId
     * @return
     */
    @GetMapping("/getPriceCase")
    public AjaxResult getPriceCase(Long activityId) {
        LambdaQueryWrapper<ActivityPriceCase> queryWrapper = Wrappers.<ActivityPriceCase>lambdaQuery()
                .eq(ActivityPriceCase::getActivityId, activityId);
        return AjaxResult.success(activityPriceCaseService.list(queryWrapper));
    }

    /**
     * 获取活动已购买人数
     */
    @GetMapping("/getStatus")
    public AjaxResult getStatus(Long activityId) {
        return activityOrderInfoService.getActivityClosed(activityId);
    }

    @GetMapping("/image/wxm/code")
    public void getWxACode(String page, String param, HttpServletResponse response) throws UnsupportedEncodingException {
        String token = WxMaUtil.getToken();
        String requestUrl = WxMaUtil.WXACODEURL + token;
        JSONObject params = new JSONObject();
        params.put("page", page);
        params.put("scene", param);
        params.put("width", 430);
        params.put("auto_color", true);
        params.put("check_path", false);
        OutputStream stream = null;
        try {
            byte[] bytes = HttpUtils.sendPostBackStream(requestUrl, params.toString());
            response.setContentType("image/png");
            stream = response.getOutputStream();
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.flush();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
