package com.example.demo.produce.serviceImp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.produce.entity.UserHead;
import com.example.demo.produce.entity.UserTable;
import com.example.demo.produce.mapper.UserTableMapper;
import com.example.demo.produce.service.IUserTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘江
 * @since 2020-10-26
 */
@Service
public class UserTableServiceImpl extends ServiceImpl<UserTableMapper, UserTable> implements IUserTableService {

    public  String Log(String name,String password){
        QueryWrapper<UserTable> userTableQueryWrapper = new QueryWrapper<>();
        userTableQueryWrapper.eq("name",name).eq("password",password);
        UserTable one = getOne(userTableQueryWrapper);
        UserHead userHead = new UserHead();
        try {
            Integer name1 = one.getId();
            userHead.setImg(String.valueOf(name1)).setCode(200);
        }catch (Exception e){
            userHead.setCode(100);
            return JSON.toJSONString(userHead);
        }
        return JSON.toJSONString(userHead);
    }
    public String logHead(String name){
        QueryWrapper<UserTable> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("name",name);
        UserTable one = getOne(objectQueryWrapper);
        UserHead userHead = new UserHead();
        try {
            String headImg = one.getHeadImg();
            userHead.setImg(headImg);
            userHead.setCode(200);
        }catch (Exception e){
            userHead.setCode(100);
        }
        return JSON.toJSONString(userHead);
    }
    public String getAll(){
        QueryWrapper<UserTable> userTableQueryWrapper = new QueryWrapper<>();
        return JSON.toJSONString(list(userTableQueryWrapper));
    }
    public boolean updateB(String info){
        JSONObject jsonObject = JSON.parseObject(info);
        String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");
        Integer id = jsonObject.getInteger("id");
        UserTable byId = getById(id);
        try {
          String imgPath = jsonObject.getString("imgPath");
          byId.setHeadImg(imgPath);
        }catch (Exception e){

        }

        byId.setName(name).setPassword(password);
        QueryWrapper<UserTable> userTableQueryWrapper = new QueryWrapper<>();
        userTableQueryWrapper.eq("id",id);
        return update(byId,userTableQueryWrapper);
    }
}
