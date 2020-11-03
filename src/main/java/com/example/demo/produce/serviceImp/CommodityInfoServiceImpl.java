package com.example.demo.produce.serviceImp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.produce.entity.CommodityFullInfo;
import com.example.demo.produce.entity.CommodityHeadImg;
import com.example.demo.produce.entity.CommodityInfo;
import com.example.demo.produce.entity.CommodityInfoImg;
import com.example.demo.produce.mapper.CommodityInfoMapper;
import com.example.demo.produce.service.ICommodityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class CommodityInfoServiceImpl extends ServiceImpl<CommodityInfoMapper, CommodityInfo> implements ICommodityInfoService {
    @Autowired
    CommodityHeadImgServiceImpl commodityHeadImgService;
    @Autowired
    CommodityInfoImgServiceImpl commodityInfoImgService;
    @Autowired
    CommodityInfoServiceImpl commodityInfoService;
    public CommodityFullInfo getInfoById(String id){
        QueryWrapper<CommodityInfo> commodityInfoQueryWrapper = new QueryWrapper<>();
        commodityInfoQueryWrapper.eq("commodity_id",id);
        CommodityInfo one = getOne(commodityInfoQueryWrapper);
        CommodityFullInfo commodityFullInfo = new CommodityFullInfo();

        try {
            one.getCommodityId();
            commodityFullInfo.setCommodityInfo(one);
            QueryWrapper<CommodityHeadImg> commodityHeadImgQueryWrapper = new QueryWrapper<>();
            commodityHeadImgQueryWrapper.eq("commodity_id",id);
            commodityFullInfo.setCommodityHeadImgList(commodityHeadImgService.list(commodityHeadImgQueryWrapper));
            QueryWrapper<CommodityInfoImg> commodityInfoImgQueryWrapper = new QueryWrapper<>();
            commodityInfoImgQueryWrapper.eq("commodity_id",id);
            commodityFullInfo.setCommodityInfoImgList(commodityInfoImgService.list(commodityInfoImgQueryWrapper));
            commodityFullInfo.setCode(200);
        }catch (Exception e){
            commodityFullInfo.setCode(100);
        }
        return commodityFullInfo; }

    public List<CommodityInfo> getAll(){
        QueryWrapper<CommodityInfo> commodityInfoQueryWrapper = new QueryWrapper<>();
        return  list(commodityInfoQueryWrapper);
    }
    public boolean add(String info){
        JSONObject jsonObject = JSON.parseObject(info);
        System.out.println(jsonObject.toJSONString());
        Integer orderId=UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
        String name = jsonObject.getString("name");
        String id = jsonObject.getString("id");
        Double price = jsonObject.getDouble("price");
        String commodityId = UUID.randomUUID().toString().substring(1, 5);
        JSONArray headImg = jsonObject.getJSONArray("headImg");
        JSONArray contentImg = jsonObject.getJSONArray("contentImg");
        CommodityInfo commodityInfo = new CommodityInfo();
        commodityInfo.setCommodityId(commodityId).setCommodityImg(headImg.getJSONObject(0).getString("imgPath"))
                .setCommodityName(name).setUserId(id).setCommodityPrice(BigDecimal.valueOf(price)).setId(orderId);
            for (int i = 0; i < headImg.size(); i++) {
                commodityHeadImgService.save(new CommodityHeadImg().setCommodityId(commodityId).setImgpaht(headImg.getJSONObject(i).getString("imgPath")).setId(orderId+i));
            }
            for (int i = 0; i < contentImg.size(); i++) {
                commodityInfoImgService.save(new CommodityInfoImg().setCommodityId(commodityId).setCommodityImg(contentImg.getJSONObject(i).getString("imgPath")).setId(orderId+i));
            }
          return   save(commodityInfo);




    }
    public String getByName(String name){
        QueryWrapper<CommodityInfo> commodityInfoQueryWrapper = new QueryWrapper<>();
        commodityInfoQueryWrapper.like("commodity_name",name);
          return   JSON.toJSONString(list(commodityInfoQueryWrapper));

    }
    public boolean delete(String id){
        QueryWrapper<CommodityInfo> commodityInfoQueryWrapper = new QueryWrapper<>();
        commodityInfoQueryWrapper.eq("commodity_id",id);

        QueryWrapper<CommodityHeadImg> commodityInfoQueryWrapper1 = new QueryWrapper<>();
        commodityInfoQueryWrapper1.eq("commodity_id",id);

        QueryWrapper<CommodityInfoImg> commodityInfoQueryWrapper2 = new QueryWrapper<>();
        commodityInfoQueryWrapper2.eq("commodity_id",id);

        remove(commodityInfoQueryWrapper);
        commodityInfoImgService.remove(commodityInfoQueryWrapper2);
       return commodityHeadImgService.remove(commodityInfoQueryWrapper1);


    }

    public boolean updateAll(String info) {
        Integer orderId=UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
          JSONObject jsonObject = JSON.parseObject(info);
//        System.out.println(jsonObject.toJSONString());
        JSONObject commodityinfo = jsonObject.getJSONObject("commodityinfo");
        System.out.println("======="+commodityinfo.toJSONString());
        CommodityInfo commodityInfos = new CommodityInfo();
        System.out.println(jsonObject.getDouble("commodityPrice"));
        commodityInfos.setCommodityImg(commodityinfo.getString("commodityImg"))
                .setCommodityId(commodityinfo.getString("commodityId"))
                .setId(commodityinfo.getInteger("id")).setCommodityPrice(
                BigDecimal.valueOf(commodityinfo.getDouble("commodityPrice")))
                .setUserId(commodityinfo.getString("userId"))
                .setCommodityName(commodityinfo.getString("commodityName"));
        String commodityId = commodityinfo.getString("commodityId");
        System.out.println("-----------"+commodityId);
        delete(commodityId);

        JSONArray commodityInfoImgList = jsonObject.getJSONArray("commodityInfoImgList");
        JSONArray commodityHradImgList = jsonObject.getJSONArray("commodityHradImgList");
        for (int i = 0; i < commodityHradImgList.size(); i++) {
            commodityHeadImgService.save(new CommodityHeadImg().setCommodityId(commodityId).setImgpaht(commodityHradImgList.getJSONObject(i).getString("imgpaht")).setId(orderId+i));
        }
        for (int i = 0; i < commodityInfoImgList.size(); i++) {
            commodityInfoImgService.save(new CommodityInfoImg().setCommodityId(commodityId).setCommodityImg(commodityInfoImgList.getJSONObject(i).getString("commodityImg")).setId(orderId+i));
        }
        return   save(commodityInfos);

    }
}
