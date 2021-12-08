package com.joolun.web.controller.course;

import com.joolun.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户书籍录音Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/system/audio")
public class UserAudioController extends BaseController {
//    @Autowired
//    private IUserAudioService userAudioService;
//
//    /**
//     * 查询用户书籍录音列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:audio:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(UserAudio userAudio) {
//        startPage();
//        List<UserAudio> list = userAudioService.selectUserAudioList(userAudio);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出用户书籍录音列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:audio:export')")
//    @Log(title = "用户书籍录音", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(UserAudio userAudio) {
//        List<UserAudio> list = userAudioService.selectUserAudioList(userAudio);
//        ExcelUtil<UserAudio> util = new ExcelUtil<UserAudio>(UserAudio.class);
//        return util.exportExcel(list, "audio");
//    }
//
//    /**
//     * 获取用户书籍录音详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:audio:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(userAudioService.selectUserAudioById(id));
//    }
//
//    /**
//     * 新增用户书籍录音
//     */
//    @PreAuthorize("@ss.hasPermi('system:audio:add')")
//    @Log(title = "用户书籍录音", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody UserAudio userAudio) {
//        return toAjax(userAudioService.insertUserAudio(userAudio));
//    }
//
//    /**
//     * 修改用户书籍录音
//     */
//    @PreAuthorize("@ss.hasPermi('system:audio:edit')")
//    @Log(title = "用户书籍录音", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody UserAudio userAudio) {
//        return toAjax(userAudioService.updateUserAudio(userAudio));
//    }
//
//    /**
//     * 删除用户书籍录音
//     */
//    @PreAuthorize("@ss.hasPermi('system:audio:remove')")
//    @Log(title = "用户书籍录音", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(userAudioService.deleteUserAudioByIds(ids));
//    }
}
