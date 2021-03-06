package xyz.mydev.transaction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.transaction.domain.Girl;
import xyz.mydev.transaction.repository.GirlRepository;
import xyz.mydev.transaction.service.GirlService;

import java.util.List;

/**
 * @author ZSP
 */
@Slf4j
@RestController
@RequestMapping("/girl")
public class GirlController {

  @Autowired
  private GirlService girlService;
  @Autowired
  private GirlRepository girlRepository;

  @PostMapping(value = "/girls")
  public Girl girlAdd(@RequestParam(value = "age") Integer age,
                      @RequestParam(value = "cupSize") String cupSize) {
    Girl girl = new Girl();
    girl.setAge(age);
    girl.setCupSize(cupSize);
    girlService.save(girl);
    System.out.println(girl.getId());
    return girl;
  }

  @GetMapping(value = "/girls/{id}")
  public Girl girlFind(@PathVariable(value = "id") Integer id) {
    return girlService.findByIdNonNull(id);
  }

  @PutMapping(value = "/girls/{id}")
  public Girl girlUpdate(@PathVariable(value = "id") Integer id,
                         @RequestParam(value = "age") Integer age,
                         @RequestParam(value = "cupSize") String cupSize) {
    Girl girl = new Girl();
    girl.setId(id);
    girl.setAge(age);
    girl.setCupSize(cupSize);
    Girl update = girlService.update(girl);
    log.info("{}", update);
    return update;
  }

  @DeleteMapping(value = "/girls/{id}")
  public void girlDelete(@PathVariable(value = "id") Integer id) {
    girlService.deleteById(id);
  }

  @GetMapping(value = "/girls")
  public List<?> findAll() {
    return girlRepository.selectList(null);
  }
}
