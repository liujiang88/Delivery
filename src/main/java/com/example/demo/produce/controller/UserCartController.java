package com.example.demo.produce.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.produce.serviceImp.UserCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘江
 * @since 2020-10-26
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/produce/user-cart")
public class UserCartController {
    @Autowired
    UserCartServiceImpl cartService;

    @PostMapping("/addCar")
    public boolean addCar(@RequestBody String info){
        System.out.println(info);
     return cartService.add(info);
    }
    @GetMapping("/getAllById")
    public String getAllById(@RequestParam String id){
        return JSON.toJSONString(cartService.getListById(id));
    }
    @PostMapping("/update")
    public boolean update(@RequestBody String info){
        return cartService.updateInfo(info);
    }
}

