package xyz.mydev.jackson.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.mydev.jackson.relationship.many2one_un.OneUser;
import xyz.mydev.jackson.vo.UserWithPlansVO;
import xyz.mydev.jackson.vo.UserWithPlansVO2;

/**
 * @author zhao
 * @date 2018/09/12 11:15
 * @description
 */
@Mapper(componentModel = "spring", uses = {ManyPlanMapper.class})
public interface OneUserMapper {
  UserWithPlansVO toDTO(OneUser oneUser);

  OneUser toOUser(UserWithPlansVO UserWithPlansVO);

  @Mapping(source = "id", target = "userId")
  @Mapping(source = "username", target = "userName")
  @Mapping(source = "manyPlans", target = "plans")
  UserWithPlansVO2 toDTO2(OneUser oneUser);

  @InheritInverseConfiguration(name = "toDTO2")
  OneUser toOUser(UserWithPlansVO2 UserWithPlansVO2);
}
