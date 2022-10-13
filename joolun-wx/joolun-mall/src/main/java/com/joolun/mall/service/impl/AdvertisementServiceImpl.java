package com.joolun.mall.service.impl;

import java.util.List;
import com.joolun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.joolun.mall.mapper.AdvertisementMapper;
import com.joolun.mall.entity.Advertisement;
import com.joolun.mall.service.IAdvertisementService;

/**
 * 广告Service业务层处理
 * 
 * @author Lanjian
 * @date 2022-10-10
 */
@Service
public class AdvertisementServiceImpl extends ServiceImpl<AdvertisementMapper, Advertisement> implements IAdvertisementService
{

}
