package life.icetea.test.shardingjdbc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import life.icetea.test.shardingjdbc.domain.TradeOrder;
import life.icetea.test.shardingjdbc.mapper.TradeOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @RequestMapping("/create")
    public String createOrder(Long userId) {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setPrice(new BigDecimal("100.01"));
        tradeOrder.setUserId(userId);

        log.info("inserting tradeOrder={}", tradeOrder);
        int insert = tradeOrderMapper.insert(tradeOrder);

        if (insert <= 0) {
            return "error";
        }

        return "success";
    }

    @PostMapping("/batch-create")
    public String batchCreateOrder(@RequestBody HashSet<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return "success";
        }

        Collection<TradeOrder> collect = userIds.stream().map(userId -> {
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setPrice(new BigDecimal("100.01"));
            tradeOrder.setUserId(userId);
            return tradeOrder;
        }).collect(Collectors.toList());


        int insert = tradeOrderMapper.batchInsert(collect);

        if (insert <= 0) {
            return "error";
        }

        return "success";
    }

    @RequestMapping("/list")
    public Object listOrder() {
        Page<TradeOrder> page = new Page<>(1, 1000);
        tradeOrderMapper.selectPage(page, null);
        return page;
    }

    @RequestMapping("/list-by-userId")
    public Object listByUserId(@RequestParam Long userId) {
        Page<TradeOrder> page = new Page<>(1, 1000);
        LambdaQueryWrapper<TradeOrder> wrapper = Wrappers.lambdaQuery(TradeOrder.class)
                .eq(TradeOrder::getUserId, userId);


        tradeOrderMapper.selectPage(page, wrapper);
        return page;
    }

    @RequestMapping("/list-by-userIds")
    public Object listByUserId(@RequestBody HashSet<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return "success";
        }

        LambdaQueryWrapper<TradeOrder> wrapper = Wrappers.lambdaQuery(TradeOrder.class)
                .in(TradeOrder::getUserId, userIds);

        List<TradeOrder> tradeOrders = tradeOrderMapper.selectList(wrapper);
        return tradeOrders;
    }

}
