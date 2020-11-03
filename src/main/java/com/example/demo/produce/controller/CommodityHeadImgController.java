package com.example.demo.produce.controller;


import com.example.demo.produce.serviceImp.CommodityHeadImgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘江
 * @since 2020-10-26
 * @email 1445613144@qq.com
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/produce/commodity-head-img")
public class CommodityHeadImgController {
     @Autowired
    CommodityHeadImgServiceImpl commodityHeadImgService;
     @GetMapping("/delete")
    public boolean deleteById(@RequestParam  Integer id){
        return  commodityHeadImgService.removeById(id) ;
    }
}

