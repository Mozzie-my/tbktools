package com.dm.tbktool.Bean.Jd;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class JdJson {
    private int code;
    private String msg;
    @SerializedName(value = "JdData",alternate = "jd_data")
    private JdData data;
}
