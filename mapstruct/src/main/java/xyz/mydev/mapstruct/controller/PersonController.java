package xyz.mydev.mapstruct.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.mapstruct.dto.PersonDTO;
import xyz.mydev.mapstruct.service.PersonService;

import java.util.List;

/**
 * @Auther: zhao
 * @date  2018/07/24:10/02
 * @description
 */
@RestController("/api")
public class PersonController {

  @Autowired
  private PersonService personService;

  @ApiOperation(value = "创建人物", notes = "根据personDTO对象创建人物")
  @ApiImplicitParam(name = "personDTO", value = "用户详细实体personDTO", required = true, dataType = "personDTO", paramType = "body")
  @PostMapping("/person")
  public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
    System.out.println(personDTO);
    return personService.save(personDTO);
  }

  @PostMapping("/person/string")
  public String createPersonByMap(@RequestBody String map) {
    System.out.println(map);
    return map;
  }

  @PutMapping("/person")
  public PersonDTO updatePerson(@RequestBody PersonDTO personDTO) {
    if (personDTO.getId() == null) {
      return createPerson(personDTO);
    } else {
      return personService.save(personDTO);
    }


  }

  @GetMapping("/person/{id}")
  public PersonDTO retrievePerson(@PathVariable Long id) {
    return personService.findOne(id);
  }

  @GetMapping("/person")
  public List<PersonDTO> retrieveAllPerson() {
    return personService.findAll();
  }

}
