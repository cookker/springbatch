package io.spring.helloworld.sec8lec36;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ColumnRangeParitioner implements Partitioner {
    private JdbcOperations jdbcTemplate;
    @Setter
    private String table;
    @Setter
    private String column;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        Integer min = jdbcTemplate.queryForObject("SELECT MIN(" + column + ") from " + table, Integer.class);
        Integer max = jdbcTemplate.queryForObject("SELECT MAX(" + column + ") from " + table, Integer.class);
        int targetSize = (max - min)/gridSize + 1;

        log.info("select min:{}, max:{}", min, max);
        int number = 0;
        int start = min;
        int end = start + targetSize - 1;

        while(start <= max){
            ExecutionContext context = new ExecutionContext();
            result.put("partition" + number, context);
            if(end >= max){
                end = max;
            }
            context.putInt("minValue", start);
            context.putInt("maxValue", end);

            start += targetSize;
            end += targetSize;
            number++;
        }
        return result;
    }

    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
