package com.dm.tbktool.Bean.Jd;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ShopInfo {
    @SerializedName(value = "shopId",alternate = "shop_id")
    private long shopId;
    @SerializedName(value = "shopLabel",alternate = "shop_label")
    private String shopLabel;
    @SerializedName(value = "shopLevel",alternate = "shop_level")
    private double shopLevel;
    @SerializedName(value = "shopName",alternate = "shop_name")
    private String shopName;
}
