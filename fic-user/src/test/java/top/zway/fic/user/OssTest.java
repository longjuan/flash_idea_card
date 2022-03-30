package top.zway.fic.user;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import top.zway.fic.user.util.OssUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ZZJ
 */
@SpringBootTest
public class OssTest {
    @Autowired
    private OssUtil ossUtil;

    @Test
    public void listFiles(){
        List<String> strings = ossUtil.listFiles();
        System.out.println(strings);
    }

    @Test
    public void uploadFile() throws IOException {
        DiskFileItem file1 = new DiskFileItem("file", "form-data", false, "xxx.jpg", 1024 * 1024 * 1024, new File("C:\\Users\\ZZJ\\Desktop\\屏幕截图 2022-03-25 210331.jpg"));
        file1.getOutputStream();
        String file = ossUtil.uploadFile(new CommonsMultipartFile(file1));
        System.out.println(file);
    }


}
