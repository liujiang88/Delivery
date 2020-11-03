package com.example.demo.produce.serviceImp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.produce.entity.UserAddress;
import com.example.demo.produce.mapper.UserAddressMapper;
import com.example.demo.produce.service.IUserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘江
 * @since 2020-10-26
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

    public String getById(String id){
        QueryWrapper<UserAddress> userAddressQueryWrapper = new QueryWrapper<>();
        userAddressQueryWrapper.eq("user_id",id);
        List<UserAddress> list = list(userAddressQueryWrapper);
        return JSON.toJSONString(list);
    }
    public boolean updateById(Integer id,String info,String name,Integer phone){
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id).setAddressInfo(info).setAddressPhone(phone).setAddressName(name);
       return updateById(userAddress);
    }
    public boolean saveAddress(String info,String name,Integer phone,String userId){
        UserAddress userAddress = new UserAddress();
        Integer orderId=UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
        userAddress.setAddressInfo(info).setAddressPhone(phone).setAddressName(name).setId(orderId).setAddressDefault(0).setUserId(userId);
        return save(userAddress);
    }
    public String get(Integer id){
      return   JSON.toJSONString(getById(id));
    }
}
