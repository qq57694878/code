package com.youi.business.hardware.web;

import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.hardware.service.HwCommonService;
import com.youi.business.hardware.service.HwStorageService;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/27.
 */
@RestController
public class HwCommonController {
    @Autowired
    private HwCommonService hwCommonService;
    @Autowired
    private JWTService jwtService;

    /**
     * 获取资产编号
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/common/get_new_asset_num")
    public RestResult getNewAssetNum(@Userid Long userid, @RequestBody Map<String,String> param) throws Exception {
        String device_type = param.get("device_type");
        String asset_num = hwCommonService.getNewAssetNum(device_type);
        Map<String,String> result = new HashMap<String,String>();
        result.put("asset_num",asset_num);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
}
