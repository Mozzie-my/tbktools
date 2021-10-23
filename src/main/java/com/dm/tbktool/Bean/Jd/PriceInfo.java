package com.dm.tbktool.Bean.Jd;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class PriceInfo {
    @SerializedName(value = "historyPriceDay",alternate = "history_price_day")
    private int historyPriceDay;
    @SerializedName(value = "lowestCouponPrice",alternate = "lowest_coupon_price")
    private int lowestCouponPrice;
    @SerializedName(value = "lowestPrice",alternate = "lowest_price")
    private int lowestPrice;
    @SerializedName(value = "lowestPriceType",alternate = "lowest_price_type")
    private int lowestPriceType;
    private int price;
}
