package com.joolun.web.api;


import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.service.IUserShareRecordService;
import com.joolun.weixin.utils.ThirdSessionHolder;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/share/record")
@Api(value = "wxuser", tags = "小程序用户API")
public class UserShareApi {

    private final IUserShareRecordService userShareRecordService;

    @GetMapping("/add")
    public AjaxResult addRecord(String shareUserId) {
        String wxUserId = ThirdSessionHolder.getThirdSession().getWxUserId();
        userShareRecordService.addShareRecord(wxUserId, shareUserId);
        return AjaxResult.success();
    }

}
