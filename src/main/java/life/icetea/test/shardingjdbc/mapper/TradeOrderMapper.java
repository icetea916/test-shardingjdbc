package life.icetea.test.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import life.icetea.test.shardingjdbc.domain.TradeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface TradeOrderMapper extends BaseMapper<TradeOrder> {

    int batchInsert(@Param("list") Collection<TradeOrder> collect);

}
