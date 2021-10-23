package com.dm.tbktool.Controller;


import com.dm.tbktool.Service.urlHandle;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
public class transForm {


    @Autowired
    urlHandle urlHandle;

    @RequestMapping(value = "/url",method = RequestMethod.GET)
   // @GetMapping("/url")
    public String url(@RequestParam("text") String text) throws ApiException, IOException {
        return urlHandle.main(text);
    }
}
