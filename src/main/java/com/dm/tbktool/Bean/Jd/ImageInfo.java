package com.dm.tbktool.Bean.Jd;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
@Data
public class ImageInfo {
    @SerializedName(value = "imageList",alternate = "image_list")
    private List<ImageList> imageList;
    @SerializedName(value = "whiteImage",alternate = "white_image")
    private String whiteImage;
}
