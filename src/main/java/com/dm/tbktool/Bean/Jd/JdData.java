package com.dm.tbktool.Bean.Jd;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
@Data
public class JdData {
    @SerializedName(value = "shortURL",alternate = "short_urlW")
    private String shortURL;
    private String note;
    private int is_coupon;
    @SerializedName(value = "couponInfo",alternate = "coupon_info")
    private List<String> couponInfo;
    @SerializedName(value = "commissionInfo",alternate = "commission_info")
    private CommissionInfo commissionInfo;
    @SerializedName(value = "priceInfo",alternate = "price_info")
    private PriceInfo priceInfo;
    @SerializedName(value = "pinGouInfo",alternate = "pin_gou_info")
    private List<String> pinGouInfo;
    @SerializedName(value = "shopInfo",alternate = "shop_info")
    private ShopInfo shopInfo;
    @SerializedName(value = "skuName",alternate = "sku_name")
    private String skuName;
    @SerializedName(value = "skuId",alternate = "sku_id")
    private long skuId;
    private String owner;
    @SerializedName(value = "inOrderCount30Days",alternate = "in_order_count30_days")
    private int inOrderCount30Days;
    @SerializedName(value = "imageInfo",alternate = "image_info")
    private ImageInfo imageInfo;
}
