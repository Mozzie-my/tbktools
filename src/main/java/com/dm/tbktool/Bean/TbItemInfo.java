package com.dm.tbktool.Bean;

import lombok.Data;

import java.util.List;
@Data
public class TbItemInfo {
    private int code;
    private String msg;
    private List<TbItemInfoData> tbItemInfoData;
}
