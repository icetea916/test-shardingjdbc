package life.icetea.test.shardingjdbc.config;

import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;

public class OrderShardingTableStrategy implements ComplexKeysShardingAlgorithm<Long> {

    private Properties properties;

    /**
     * 分表字段key
     */
    public static final String SHARDING_COLUMN_KEY = "shardingColumn";

    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Long> complexKeysShardingValue) {
        String shardingColumName = properties.getProperty(SHARDING_COLUMN_KEY);
        if (shardingColumName == null || shardingColumName.isEmpty()) {
            throw new IllegalArgumentException("shardingColumn is not set");
        }
        String[] tableNameArrays = new String[collection.size()];
        collection.toArray(tableNameArrays);
        Collection<String> results = new LinkedHashSet<>();
        int tableCount = collection.size();

        Map<String, Collection<Long>> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        for (Long v : columnNameAndShardingValuesMap.get(shardingColumName)) {
            int index = Math.abs(v.hashCode()) % tableCount;
            results.add(tableNameArrays[index]);
        }


        return results;
    }

    @Override
    public Properties getProps() {
        return properties;
    }

    @Override
    public void init(Properties properties) {
        this.properties = properties;
    }

}
