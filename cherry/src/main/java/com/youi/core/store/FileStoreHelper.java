package com.youi.core.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileStoreHelper implements StoreHelper {

    private static Logger logger = LoggerFactory
            .getLogger(FileStoreHelper.class);

    public StoreResult getStore(String file_path) throws Exception {
        File file = new File(file_path);
        if (!file.exists()) {
            logger.info("cannot find : {}", file);
            return null;
        }
        StoreResult result = new StoreResult();
        result.setDataSource(new FileDataSource(file));
        result.setFile_size(file.length());
       return  result;
    }

    public void removeStore(String file_path) throws Exception {
        File file = new File(file_path);
        file.delete();
    }

    public StoreResult saveStore(String file_path, DataSource dataSource)
            throws Exception {
        File targetFile = new File(file_path);
        File parent = targetFile.getParentFile();
        FileOutputStream fos = null;
        long file_size=0;
        try {
            if (!parent.exists()) {
                parent.mkdirs();
            }
            fos = new FileOutputStream(targetFile);
            file_size = FileCopyUtils.copy(dataSource.getInputStream(), fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(fos!=null){
                fos.close();
            }
        }
        StoreResult result = new StoreResult();
        result.setDataSource(new FileDataSource(targetFile));
        result.setFile_size(file_size);
        return result;
    }


}
