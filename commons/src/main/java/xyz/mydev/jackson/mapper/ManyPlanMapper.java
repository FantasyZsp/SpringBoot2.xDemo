package xyz.mydev.jackson.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.mydev.jackson.relationship.many2one_un.ManyPlan;
import xyz.mydev.jackson.vo.MPlanVO;
import xyz.mydev.jackson.vo.UserWithPlansVO2;

/**
 * @author zhao
 * @date 2018/09/12 11:53
 * @description
 */
@Mapper(componentModel = "spring")
public interface ManyPlanMapper {

  @Mapping(source = "planId", target = "id")
  @Mapping(target = "oneUser", ignore = true)
  ManyPlan toMPlan(UserWithPlansVO2.Plan plan);

  @InheritInverseConfiguration(name = "toMPlan")
  UserWithPlansVO2.Plan toDTO2Plan(ManyPlan manyPlan);

  MPlanVO toMPlanVO(ManyPlan manyPlan);


  @Mapping(target = "oneUser", ignore = true)
  ManyPlan toMPlan(MPlanVO manyPlanVO);
}
