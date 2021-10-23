package com.dm.tbktool.Bean.Jd;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CommissionInfo {
    private double commission;
    @SerializedName(value = "commissionShare",alternate = "commission_share")
    private int commissionShare;
    @SerializedName(value = "couponCommission",alternate = "coupon_commission")
    private double couponCommission;
    @SerializedName(value = "endTime",alternate = "end_time")
    private long endTime;
    @SerializedName(value = "isLock",alternate = "is_lock")
    private int isLock;
    @SerializedName(value = "plusCommissionShare",alternate = "plus_commission_share")
    private int plusCommissionShare;
    @SerializedName(value = "startTime",alternate = "start_time")
    private long startTime;
}
