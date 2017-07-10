package com.youi.business.sysresource.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.sysresource.service.SysOrgService;
import com.youi.core.codetable.CodeTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/26.
 */
@RestController
public class SysOrgController {
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private JWTService jwtService;
    /**
     * 禁用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/org/get_biz_org_list")
    public RestResult getSysOrgList(@Userid Long userid) throws Exception {
        List<Map<String,Object>> list = sysOrgService.getSysOrgList();
       /* List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        Map<String,String> map = CodeTableUtil.getCodeMap("SYSORG");
        if(map!=null){
            for(String key:map.keySet()){
                Map m = new HashMap<String, Object>();
                m.put("code",key);
                m.put("value",map.get(key));
                result.add(m);
            }
        }*/
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("list_data",list);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);

    }
}
