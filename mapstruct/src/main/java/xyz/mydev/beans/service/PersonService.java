package xyz.mydev.beans.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.mydev.beans.domain.Person;
import xyz.mydev.beans.dto.PersonDTO;
import xyz.mydev.beans.mapper.PersonMapper;
import xyz.mydev.beans.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  zhao
 * @date  2018/07/23:21/36
 * @description
 */
@Service
@Transactional
public class PersonService {

  private PersonRepository personRepository;

  private PersonMapper personMapper;

  private ObjectMapper objectMapper;


  public PersonService(PersonRepository personRepository, PersonMapper personMapper, ObjectMapper objectMapper) {
    this.personMapper = personMapper;
    this.personRepository = personRepository;
    this.objectMapper = objectMapper;

  }

  public PersonDTO save(PersonDTO personDTO) {
    System.out.println("service save");
    Person person = personMapper.toEntity(personDTO);
    person = personRepository.save(person);
    PersonDTO result = personMapper.toDTO(person);
    return result;
  }

  public PersonDTO save(String personDTO) {
    System.out.println("service String save \n " + personDTO);
    PersonDTO result = null;
    try {
      result = objectMapper.readValue(personDTO, PersonDTO.class);
//            result = Json2Bean.json2Object(personDTO,PersonDTO.class);

      Person person = personMapper.toEntity(result);
      person = personRepository.save(person);
      result = personMapper.toDTO(person);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

  public PersonDTO findOne(Long id) {
    Person person = personRepository.findById(id).orElseThrow();
    return personMapper.toDTO(person);
  }

  public List<PersonDTO> findAll() {
    List<Person> listPersons = personRepository.findAll();
    List<PersonDTO> listDTO = new ArrayList<>();
    for (Person person : listPersons) {
      listDTO.add(personMapper.toDTO(person));
    }
    return listDTO;
  }
}
