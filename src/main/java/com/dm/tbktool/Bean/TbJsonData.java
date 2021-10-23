package com.dm.tbktool.Bean;

import lombok.Data;

import java.util.Date;

@Data
public class TbJsonData {
    private int categoryId;
    private String couponClickUrl;
    private Date couponEndTime;
    private String couponInfo;
    private int couponRemainCount;
    private Date couponStartTime;
    private int couponTotalCount;
    private String itemId;
    private String itemUrl;
    private String maxCommissionRate;
    private int rewardInfo;
    private String coupon;
}
