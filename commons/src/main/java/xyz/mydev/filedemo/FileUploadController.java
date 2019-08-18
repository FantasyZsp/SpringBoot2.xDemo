package xyz.mydev.filedemo;

import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.mydev.vo.ResultVO;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static xyz.mydev.util.StringUtil.generateFilePath;
import static xyz.mydev.vo.ResultVO.success;

/**
 * @author ZSP
 * @date 2018/11/21 19:36
 * @description
 */
@RestController
@RequestMapping("/file")
@Api(tags = "FileUploadController", description = "文件上传")
public class FileUploadController {

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResultVO<Object> upload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("vessel_voyage_number") String vesselVoyageNumber,
                                 @RequestParam("job_queue_code") String jobQueueCode,
                                 @RequestParam("set_scaling") boolean setScaling,
                                 @RequestParam(required = false, value = "scaling_ratio") Float scalingRatio) throws IOException {
    System.out.println(file.getClass());


    String destPath = "D:/fileTest/" + file.getOriginalFilename();
    File destFile = new File(destPath);
    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
    return success(file.getSize());
  }

  @PostMapping("/multi")
  public ResultVO<Object> upload2(@RequestParam("file") MultipartFile[] file) {
    System.out.println(file.getClass());
    return success(file);
  }

  public static void main(String[] args) {

    String driverPath = "D:/Picture/Original";
    String date = LocalDate.now().toString();
    String vesselVoyageNumber = "20181126";
    String jobQueueCode = UUID.randomUUID().toString();
    System.out.println(generateFilePath('/', driverPath, date, vesselVoyageNumber, jobQueueCode, "fileName.png"));
  }

  public void fileUpload() {

  }
}
