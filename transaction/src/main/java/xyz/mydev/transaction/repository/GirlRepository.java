package xyz.mydev.transaction.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.mydev.transaction.domain.Girl;

/**
 * @author ZSP
 */
@Repository
@Mapper
public interface GirlRepository extends BaseMapper<Girl> {

  Girl selectByIdInLockMode(@Param("id") Integer id);
}
