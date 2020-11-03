package com.example.demo.produce.serviceImp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.produce.entity.UserOrder;
import com.example.demo.produce.entity.UserOrderFull;
import com.example.demo.produce.entity.UserOrderInfo;
import com.example.demo.produce.mapper.UserOrderMapper;
import com.example.demo.produce.service.IUserOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements IUserOrderService {

       @Autowired
       UserOrderInfoServiceImpl userOrderInfoService;
       @Autowired
       UserCartServiceImpl cartService;


       public boolean addOrder(String info){

           JSONObject jsonObject = JSON.parseObject(info);
           System.out.println(jsonObject.toJSONString());
           JSONArray commodityinfo = jsonObject.getJSONArray("commodityinfo");
           String userId = commodityinfo.getJSONObject(0).getString("userId");
           String addressId = jsonObject.getString("addressId");
           String leaveWord = jsonObject.getString("leaveWord");
           Double countprice = jsonObject.getDouble("countprice");
           Integer orderId=UUID.randomUUID().toString().hashCode();
           orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
           String s = String.valueOf(orderId);
           String substring = s.substring(1, s.length() / 2);
           Integer integer = Integer.valueOf(substring);
           for (int i = 0; i < commodityinfo.size(); i++) {
               Integer id = commodityinfo.getJSONObject(i).getInteger("id");
               cartService.deleteById(id);
               UserOrderInfo userOrderInfo = new UserOrderInfo();
               userOrderInfo.setOrderId(orderId).setCommodityId(commodityinfo.getJSONObject(i).getString("commodityId"))
                       .setCommodityNumber(commodityinfo.getJSONObject(i).getInteger("commodityNumber")).setId(integer+i)
                       .setCommodityImg(commodityinfo.getJSONObject(i).getString("commodityImg"))
                       .setCommodityName(commodityinfo.getJSONObject(i).getString("commodityName"))
                       .setCommodityPrices(commodityinfo.getJSONObject(i).getDouble("commodityPrice"));
               userOrderInfoService.save(userOrderInfo);
           }
           UserOrder userOrder = new UserOrder();
           userOrder.setUserId(userId).setAddressId(addressId).setLeaveWord(leaveWord).setState(1)
                  .setCreatTime(new Date()).setUpdateTime(new Date()).setId(orderId).setLeaveWord(leaveWord).setOrderId(orderId)
                   .setPrice(countprice);
           return save(userOrder);
       }
       public boolean updateState(String info){
           JSONObject jsonObject = JSON.parseObject(info);
           Integer id = jsonObject.getInteger("id");
           Integer state = jsonObject.getInteger("state");
           UpdateWrapper<UserOrder> userOrderUpdateWrapper = new UpdateWrapper<>();
           userOrderUpdateWrapper.eq("id",id);
          return update(new UserOrder().setState(state),userOrderUpdateWrapper);
       }
       public String getListByState(String id){
           QueryWrapper<UserOrder> userOrderQueryWrapper = new QueryWrapper<>();
           userOrderQueryWrapper.eq("user_id",id);
           List<UserOrder> list = list(userOrderQueryWrapper);
           ArrayList<UserOrderFull> userOrderFulls = new ArrayList<>();
           for (int i = 0; i < list.size(); i++) {
               UserOrderFull userOrder = new UserOrderFull();
               QueryWrapper<UserOrderInfo> userOrderInfoQueryWrapper = new QueryWrapper<>();
               userOrderInfoQueryWrapper.eq("order_id",list.get(i).getOrderId());
               List<UserOrderInfo> list1 = userOrderInfoService.list(userOrderInfoQueryWrapper);
               userOrder.setUserOrder(list.get(i)).setUserOrderInfoList(list1);
               userOrderFulls.add(userOrder);
           }
           return JSON.toJSONString(userOrderFulls);
       }
}
