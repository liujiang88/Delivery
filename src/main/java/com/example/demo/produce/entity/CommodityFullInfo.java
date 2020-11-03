package com.example.demo.produce.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CommodityFull对象", description="")
public class CommodityFullInfo {
    private CommodityInfo commodityInfo;
    private List<CommodityInfoImg> commodityInfoImgList;
    private List<CommodityHeadImg> commodityHeadImgList;
    private Integer code;
}
