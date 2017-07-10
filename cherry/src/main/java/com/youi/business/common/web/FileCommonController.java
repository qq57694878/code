package com.youi.business.common.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.store.StoreConnector;
import com.youi.business.common.store.StoreDTO;
import com.youi.business.common.vo.RestResult;
import com.youi.core.store.MultipartFileDataSource;
import com.youi.core.store.MultipartHandler;
import com.youi.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/10.
 */

@RestController
public class FileCommonController {

    private static Logger logger = LoggerFactory.getLogger(FileCommonController.class);
    @Autowired
    private StoreConnector storeConnector;
    @Autowired
    private MultipartResolver multipartResolver;
    @Autowired
    private JWTService jwtService;


    /**
     * 上传任务
     * @param userid
     * @return
     */
    @RequestMapping("/file/generic/upload")
    public Object uploadAttachment(@Userid Long userid, HttpServletRequest request, HttpServletResponse response)throws Exception {
        try{
            MultipartHandler multipartHandler = new MultipartHandler(this.multipartResolver);
            multipartHandler.handle(request);
            //文件上传
            if (multipartHandler.getMultiFileMap() != null) {
                for (Map.Entry<String, List<MultipartFile>> entry : multipartHandler.getMultiFileMap().entrySet()) {
                    List<MultipartFile> value = entry.getValue();
                    if ((value == null) || (value.isEmpty())) {
                        continue;
                    }
                    MultipartFile multipartFile = value.get(0);
                    if ((multipartFile.getName() == null)
                            || "".equals(multipartFile.getName().trim())) {
                        continue;
                    }
                    if (multipartFile.getSize() == 0) {
                        continue;
                    }
                    StoreDTO dto = storeConnector.saveStore(new MultipartFileDataSource(multipartFile),"0");
                    Map<String,Object> m = new HashMap<String,Object>();
                    m.put("url",dto.getUrl());
                    m.put("file_type",dto.getFile_type());
                    m.put("file_name",dto.getShow_name());
                    m.put("file_id",String.valueOf(dto.getId()));
                    String token =jwtService.getToken(userid);
                    return new RestResult(token,m);
                }
            }
        }catch (Exception e){
           e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return new RestResult(Constants.ERROR_CODE_500);
    }

    /**
     * 获得任务详细
     * @param param
     * @return
     */
    @RequestMapping("/file/generic/delete")
    public RestResult removeAttachment(@Userid Long userid,@RequestBody Map<String,Object> param) throws Exception{
        param.put("userid",String.valueOf(userid));
        //获得工作流详细
        storeConnector.removeStore(Long.parseLong(String.valueOf(param.get("file_id"))));
        String token =jwtService.getToken(userid);
        return new RestResult(token,new Object());
    }
}
