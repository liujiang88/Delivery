package com.example.demo.produce.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.produce.entity.UserTable;
import com.example.demo.produce.serviceImp.UserTableServiceImpl;
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
@RequestMapping("/produce/user-table")
public class UserTableController {
    @Autowired
    UserTableServiceImpl userTableService;

    @GetMapping("/login")
    public  String Login(@RequestParam String name, @RequestParam String password){
        System.out.println(name+password);
     return   userTableService.Log(name, password);
    }
    @GetMapping("/checkHead")
    public String CheckHead(@RequestParam String name){
        return userTableService.logHead(name);
    }
    @GetMapping("/getAll")
    public String getAll(){
        return userTableService.getAll();
    }
    @PostMapping("/update")
    public boolean updateB(@RequestBody String info){
        return userTableService.updateB(info);
    }
    @GetMapping("/getById")
    public String getById(@RequestParam String id){
        return JSON.toJSONString(userTableService.getById(id));
    }
}

