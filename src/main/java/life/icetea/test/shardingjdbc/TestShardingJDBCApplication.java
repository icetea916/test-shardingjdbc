package life.icetea.test.shardingjdbc;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("life.icetea.test.shardingjdbc.mapper")
public class TestShardingJDBCApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestShardingJDBCApplication.class);
    }

}