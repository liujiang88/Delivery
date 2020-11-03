package com.example.demo.produce.serviceImp;

import com.example.demo.produce.entity.UserOrderInfo;
import com.example.demo.produce.mapper.UserOrderInfoMapper;
import com.example.demo.produce.service.IUserOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘江
 * @since 2020-10-29
 */
@Service
public class UserOrderInfoServiceImpl extends ServiceImpl<UserOrderInfoMapper, UserOrderInfo> implements IUserOrderInfoService {

}
