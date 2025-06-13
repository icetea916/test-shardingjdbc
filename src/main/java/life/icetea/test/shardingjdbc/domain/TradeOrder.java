package life.icetea.test.shardingjdbc.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.BigDecimalTypeHandler;

import java.math.BigDecimal;
import java.util.Date;

@TableName("trade_order")
@Data
public class TradeOrder {

    @TableId
    private Long id;

    private Long userId;

    @TableField(typeHandler = BigDecimalTypeHandler.class)
    private BigDecimal price;

    private Date createTime;
    private Date modifyTime;

}
