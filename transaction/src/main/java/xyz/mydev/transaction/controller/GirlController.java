package xyz.mydev.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.transaction.domain.Girl;
import xyz.mydev.transaction.service.GirlService;

import java.util.List;

/**
 * @author ZSP
 */
@RestController
public class GirlController {

  @Autowired
  private GirlService girlService;

  @GetMapping(value = "/girls")
  public List<Girl> girlList() {
    return girlService.findAll();
  }

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
    return girlService.update(girl);
  }

  @DeleteMapping(value = "/girls/{id}")
  public void girlDelete(@PathVariable(value = "id") Integer id) {
    girlService.deleteById(id);
  }
}
