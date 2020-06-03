package xyz.mydev.jackson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.jackson.service.RecordService;

/**
 * @author zhao
 * @date 2018/09/11 20:20
 * @description
 */
@RestController
@RequestMapping("/record")
public class RecordController {

  @Autowired
  private RecordService recordService;

  @GetMapping("/o2o/count")
  public Object createRecords(@RequestParam(required = false, value = "1000") Integer count) {

    return recordService.createRecords4o2o(count);

  }

  @GetMapping("/m2o/count")
  public Object createRecords2(@RequestParam(required = false, value = "1000") Integer count) {

    return recordService.createRecords4m2o(count);

  }

  @GetMapping("/m2m/count")
  public Object createTeachersAndStudents(@RequestParam(required = false, value = "1000") Integer count) {
    return recordService.createTeachersAndStudents(count);

  }

}
