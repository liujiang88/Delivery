package com.example.demo.produce.serviceImp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.produce.entity.CommodityInfo;
import com.example.demo.produce.entity.UserCart;
import com.example.demo.produce.entity.UserOrder;
import com.example.demo.produce.mapper.UserCartMapper;
import com.example.demo.produce.service.IUserCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class UserCartServiceImpl extends ServiceImpl<UserCartMapper, UserCart> implements IUserCartService {
    @Autowired
    CommodityInfoServiceImpl commodityInfoService;
    //添加购物车
    public boolean add(String info){
        JSONObject jsonObject = JSON.parseObject(info);
        String userId = jsonObject.getString("userId");
        Integer commodityNumber = jsonObject.getInteger("commodityNumber");
        String commodityId = jsonObject.getString("commodityId");
         QueryWrapper<UserCart> userCartQueryWrapper = new QueryWrapper<>();
          userCartQueryWrapper.eq("user_id",userId).eq("commodity_id",commodityId);
          UserCart one = getOne(userCartQueryWrapper);
         try {
             //判断    有加库存没有重新添加
             one.getCommodityId();
             UpdateWrapper<UserCart> userCartUpdateWrapper = new UpdateWrapper<>();
             userCartUpdateWrapper.eq("commodity_id",commodityId);
             one.setCommodityNumber(one.getCommodityNumber()+commodityNumber);
             return update(one,userCartUpdateWrapper);
         }catch (NullPointerException e){
             QueryWrapper<CommodityInfo> commodityInfoQueryWrapper = new QueryWrapper<>();
             commodityInfoQueryWrapper.eq("commodity_id",commodityId);
             CommodityInfo one1 = commodityInfoService.getOne(commodityInfoQueryWrapper);
             Integer orderId=UUID.randomUUID().toString().hashCode();
             orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
             return save(new UserCart().setUserId(userId).setCommodityId(commodityId).setCommodityNumber(commodityNumber).setId(orderId)
                   .setCommodityName(one1.getCommodityName()).setCommodityImg(one1.getCommodityImg()).setCommodityPrice(one1.getCommodityPrice()));
          }

    }
    //通过用户名id获取购物车
    public List<UserCart> getListById(String id){
        QueryWrapper<UserCart> userCartQueryWrapper = new QueryWrapper<>();
        userCartQueryWrapper.eq("user_id",id);
        List<UserCart> list = list(userCartQueryWrapper);
        return list;
    }
    //修改库存
    public boolean updateInfo(String info){
        JSONObject jsonObject = JSON.parseObject(info);
        String userId = jsonObject.getString("userId");
        String commodityId = jsonObject.getString("CommodityId");
        UpdateWrapper<UserCart> userCartUpdateWrapper = new UpdateWrapper<>();
        userCartUpdateWrapper.eq("user_id",userId).eq("commodity_id",commodityId);
        UserCart userCart = new UserCart();
        Integer commodityNumber = jsonObject.getInteger("commodityNumber");
        userCart.setCommodityNumber(commodityNumber);
        return update(userCart,userCartUpdateWrapper);
    }
    public boolean deleteById(Integer id){
       return removeById(id);
    }
}
