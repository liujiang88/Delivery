package com.example.demo.produce.controller;


import com.example.demo.produce.serviceImp.UserAddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.PortUnreachableException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘江
 * @since 2020-10-27
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/produce/user-address")
public class UserAddressController {
    @Autowired
    UserAddressServiceImpl userAddressService;
    @GetMapping("/getById")
    public String getById(@RequestParam String id){
        return userAddressService.getById(id);
    }
    @GetMapping("/updateById")
    public  boolean updateById(@RequestParam String name,@RequestParam Integer id,@RequestParam String info,@RequestParam Integer phone){
        return userAddressService.updateById(id,info,name,phone);
    }
    @GetMapping("/save")
    public  boolean saveAddress(@RequestParam String name,@RequestParam String info,@RequestParam Integer phone,@RequestParam String userId){
        return userAddressService.saveAddress(info,name,phone,userId);
    }
    @GetMapping("/getId")
    public String getId(@RequestParam Integer id){
        return userAddressService.get(id);
    }
}

