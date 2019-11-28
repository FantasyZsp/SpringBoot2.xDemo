package xyz.mydev.beans.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.mydev.beans.domain.Person;
import xyz.mydev.beans.dto.PersonDTO;

/**
 * @author zhao
 * @date 2018/07/23:21/25
 * @description 引用外部类AddressMapper来对address属性进行映射。mapstruct对检测方法参数与方法进行匹配。
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface PersonMapper {
  @Mapping(source = "namee", target = "name")
  Person toEntity(PersonDTO personDTO);

  /**
   * @description Entity和DTO互转时，不必全都标注。使用@InheritInverseConfiguration注解，name指出另一个方法名。
   * @auther: ZSP
   * @date 2018/7/25
   * @param: [person]
   * @return: PersonDTO
   */
  @InheritInverseConfiguration(name = "toEntity")
  PersonDTO toDTO(Person person);
}
