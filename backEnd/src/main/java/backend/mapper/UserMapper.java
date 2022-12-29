package backend.mapper;

import backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2022-12-29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
