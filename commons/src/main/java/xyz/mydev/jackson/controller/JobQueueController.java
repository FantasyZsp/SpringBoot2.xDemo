package xyz.mydev.jackson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.jackson.dto.JobQueueDTO;
import xyz.mydev.jackson.repository.JobQueueRepository;
import xyz.mydev.jackson.repository.PlanRepository;

import java.io.IOException;

/**
 * @author  zhao
 * @date  2018/09/07 15:00
 * @description  测试序列化
 */
@RestController
@RequestMapping("/job-queue")
public class JobQueueController {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JobQueueRepository jobQueueRepository;

  @Autowired
  private PlanRepository timeTestRepository;


  Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping
  public JobQueueDTO createJobQueue(@RequestBody JobQueueDTO jobQueueDTO) throws IOException {


    logger.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jobQueueDTO));
    jobQueueDTO = jobQueueRepository.save(jobQueueDTO);
    return jobQueueDTO;
  }

  @GetMapping("/{id}")
  public JobQueueDTO getJobQueue(@PathVariable Long id) throws IOException {


    JobQueueDTO jobQueueDTO = jobQueueRepository.findById(id).orElseThrow();
    logger.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jobQueueDTO));
    return jobQueueDTO;
  }


}
