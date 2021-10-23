package com.dm.tbktool.Bean;

import lombok.Data;

@Data
public class TbShortUrl {
    private int code;
    private String msg;
    private TbShortUrlData tbShortUrlData;
}
