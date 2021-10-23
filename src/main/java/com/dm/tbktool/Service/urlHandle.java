package com.dm.tbktool.Service;


import com.dm.tbktool.Bean.*;
import com.dm.tbktool.Bean.Jd.JdJson;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.org.apache.bcel.internal.generic.Select;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkTpwdCreateResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class urlHandle {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Gson gson;
    public String main(String text) throws ApiException, IOException {
        //判断是否为链接
        if (text.indexOf("http")!=-1){
            if (text.indexOf("aobao.com")!=-1||text.indexOf("tmall.com")!=-1||text.indexOf("s.click.Taobao")!=-1){
                return tbUrlTrans(text);
            }else if (text.indexOf("jd.com")!=-1){
                return jdTrans(text);
            }else if (text.indexOf("duo.com")!=-1){
                return pddTrans(text);
            }else{
                return "没有淘宝、京东或多多的链接";
            }
        }else{
            String pattern = "([\\p{Sc}])\\w{8,12}([\\p{Sc}])";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(text);
            if (m.find()) {
                return tbTklTrans(m.group());
            }else{
                return "没有发现淘口令或者链接";
            }
        }
    }

    /**
     * 淘口令转链接
     * @param text 淘口令
     * @return
     */
    private String tbTklTrans(String text) throws ApiException {

        System.out.println(text);
        String url="http://api.tbk.dingdanxia.com/tbk/tkl_privilege";

        Map<String, String> jsonMap = new HashMap<>(6);
        jsonMap.put("apikey","lu3TvtwFypAnhavz4vtQJstSCB0PHf2X");
        jsonMap.put("tkl", text);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        url,
                        generatePostJson(jsonMap),
                        String.class
                );
        //格式化json
        String json=apiResponse.getBody().replace("data","tb_json_data");
        System.out.println(json);
        TbJson tbJson = gson.fromJson(json, TbJson.class);
        return tbBody(tbJson);

    }

    private String tbUrlTrans(String text) throws ApiException, IOException {
        //正则匹配链接
        String url = getUrl(text);
        //取链接的id
        String id;
        if (url.indexOf("s.click.Taobao")!=-1){
            id=originUrlToId(shortUrlToOriginUrl(url));
        }else {
            id=originUrlToId(url);
        }
        System.out.println(id);
        Map<String, String> jsonMap = new HashMap<>(6);
        String apiurl="http://api.tbk.dingdanxia.com/tbk/id_privilege";
        jsonMap.put("apikey","lu3TvtwFypAnhavz4vtQJstSCB0PHf2X");
        jsonMap.put("id", id);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        apiurl,
                        generatePostJson(jsonMap),
                        String.class
                );
        //格式化json
        String json=apiResponse.getBody().replace("data","tb_json_data");
        TbJson tbJson = gson.fromJson(json, TbJson.class);
        return tbBody(tbJson);
    }

    /**
     * 淘宝文案整合
     */
    private String tbBody(TbJson tbJson) throws ApiException {
        //解析获得短链接
        String url="http://api.tbk.dingdanxia.com/tbk/spread_get";
        Map<String, String> jsonMap = new HashMap<>(6);
        jsonMap.put("apikey","lu3TvtwFypAnhavz4vtQJstSCB0PHf2X");
        jsonMap.put("url", tbJson.getTbJsonData().getCouponClickUrl());
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        url,
                        generatePostJson(jsonMap),
                        String.class
                );
        String json = apiResponse.getBody().replace("data","tb_short_url_data");
        TbShortUrl tbShortUrl=gson.fromJson(json,TbShortUrl.class);

        //解析获得淘口令
        url="http://gw.api.taobao.com/router/rest";
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "31307711", "2204bdd8cd38b7a818f6cbb847108f82");
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
        req.setUrl(tbShortUrl.getTbShortUrlData().getContent());
        TbkTpwdCreateResponse response = client.execute(req);
        json = response.getBody().replace("data","tbk_tpwd_create_response_data");
        System.out.println(json);
        TbkTpwdCreateRoot tbkTpwdCreateRoot=gson.fromJson(json,TbkTpwdCreateRoot.class);
        System.out.println(tbkTpwdCreateRoot.getTbkTpwdCreateResponse());
        //解析获得商品信息
        url="http://api.tbk.dingdanxia.com/tbk/item_info";
        jsonMap.clear();
        jsonMap.put("apikey","lu3TvtwFypAnhavz4vtQJstSCB0PHf2X");
        jsonMap.put("num_iids",tbJson.getTbJsonData().getItemId());
        apiResponse = restTemplate.postForEntity
                (
                        url,
                        generatePostJson(jsonMap),
                        String.class
                );
        json = apiResponse.getBody().replace("data","tb_item_info_data");
       // System.out.println(json);
        TbItemInfo tbItemInfo=gson.fromJson(json,TbItemInfo.class);
        //最终价格计算 折后价减去优惠券面额 四舍五入保留两位小数
        DecimalFormat   fnum   =   new DecimalFormat("##0.00");
        String final_price= fnum.format(Float.parseFloat(tbItemInfo.getTbItemInfoData().get(0).getZkFinalPrice())-Float.parseFloat(tbJson.getTbJsonData().getCoupon()));
        return  "原价："+tbItemInfo.getTbItemInfoData().get(0).getReservePrice()+"元\n"
                +"现价："+final_price+"元\n"
                +tbkTpwdCreateRoot.getTbkTpwdCreateResponse().getTbkTpwdCreateResponseData().getModel()+"\n"
                +"图片："+tbItemInfo.getTbItemInfoData().get(0).getSmallImages().getString().get(0)+"\n"
                +"短链接："+tbShortUrl.getTbShortUrlData().getContent();

    }

    private String jdTrans(String text){
        String url=getUrl(text);
        String apiurl="http://api.tbk.dingdanxia.com/jd/by_unionid_promotion";
        Map<String, String> jsonMap = new HashMap<>(6);
        jsonMap.put("apikey","lu3TvtwFypAnhavz4vtQJstSCB0PHf2X");
        jsonMap.put("materialId", url);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        apiurl,
                        generatePostJson(jsonMap),
                        String.class
                );
        String json = apiResponse.getBody().replace("data","jd_data");
        System.out.println(json);
        JdJson jdJson = gson.fromJson(json,JdJson.class);
        return "商品名称："+jdJson.getData().getSkuName()+"\n"
                +"价格："+String.valueOf(jdJson.getData().getPriceInfo().getLowestPrice())+"\n"
                +"链接："+jdJson.getData().getShortURL()+"\n"
                +"图片："+jdJson.getData().getImageInfo().toString();

    }

    private String pddTrans(String text){
        return "3";
    }

    /**
     * 短链接转换为长连接
     * @param url
     * @return 长链接
     * @throws IOException
     */
    private String shortUrlToOriginUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.request().url().toString();
    }

    /**
     * 根据原始链接获得id
     * @param url
     * @return id
     */
    private String originUrlToId(String url){
        String apiurl="http://api.tbk.dingdanxia.com/tbk/uland_query";
        Map<String, String> jsonMap = new HashMap<>(6);
        jsonMap.put("apikey","lu3TvtwFypAnhavz4vtQJstSCB0PHf2X");
        jsonMap.put("url", url);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        apiurl,
                        generatePostJson(jsonMap),
                        String.class
                );
        String json = apiResponse.getBody().replace("data","uland_to_id_data");
        UlandToId ulandToId=gson.fromJson(json,UlandToId.class);
        return ulandToId.getUlandToIdData().getNumIid();
    }

    /**
     * 正则匹配链接
     * @param text
     * @return 匹配好的链接
     */
    private String getUrl(String text){
        String pattern="(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return m.group();
        }else{
            return "没有发现链接";
        }
    }

    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");

        httpHeaders.setContentType(type);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(jsonMap, httpHeaders);

        return httpEntity;
    }
}
