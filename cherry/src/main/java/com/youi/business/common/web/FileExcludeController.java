package com.youi.business.common.web;

import com.youi.business.common.store.StoreConnector;
import com.youi.business.common.store.StoreDTO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Created by jinliang on 2016/9/10.
 */

@Controller
public class FileExcludeController {
    @Autowired
    private StoreConnector storeConnector;
    /**
     * 读取带跟踪的图片
     */
  /*  @ResponseBody
    @RequestMapping(value = "/exclude/file/downloadbak/{attachment_id}")
    public ResponseEntity<byte[]> downloadbak(@PathVariable("attachment_id") Long attachment_id, HttpServletResponse response)throws Exception {
        StoreDTO dto = storeConnector.getStore(attachment_id);
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpHeaders headers = new HttpHeaders();
        String fileName=new String(dto.getShow_name().getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        byte[]bytes = IOUtils.toByteArray(dto.getDataSource().getInputStream());
        headers.add("Content-Length", "" + bytes.length);
        return new ResponseEntity<byte[]>(bytes, responseHeaders, HttpStatus.CREATED);
    }*/
    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/exclude/file/download/{attachment_id}")
    public String download(@PathVariable("attachment_id") Long attachment_id, HttpServletRequest request,HttpServletResponse response)throws Exception {
        StoreDTO dto = storeConnector.getStore(attachment_id);
        OutputStream os = response.getOutputStream();
        InputStream inputStream =dto.getDataSource().getInputStream();
        try {
            response.reset();
            String userAgent = request.getHeader("User-Agent");
            boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);
            String fileName=new String(dto.getShow_name().getBytes("UTF-8"),"iso-8859-1");
            if(isIE){
                fileName = URLEncoder.encode(fileName, "UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            }else{
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);//inline;
            }

            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "must-revalidate, no-transform");
            response.setDateHeader("Expires", 0L);
           // response.setContentType("application/x-download");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            byte[]bytes = IOUtils.toByteArray(dto.getDataSource().getInputStream());
            response.setHeader("Content-Length","" + bytes.length);
            os.write(bytes);
            os.flush();
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            if (os != null) {
                os.close();
            }
            if(inputStream!=null){
                inputStream.close();;
            }
        }


      /*  try {
            String fileName=new String(dto.getShow_name().getBytes("UTF-8"),"iso-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
           // response.setContentType("application/octet-stream; charset=utf-8");
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            // response.setContentType("multipart/form-data");
            byte[] b = new byte[4096];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.flush();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if(inputStream!=null){
                inputStream.close();;
            }
        }*/
        return null;
    }
}
