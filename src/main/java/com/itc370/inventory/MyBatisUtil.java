package com.itc370.inventory;

import javax.sql.DataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class MyBatisUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // Setup PostgreSQL data source
            DataSource dataSource = new PooledDataSource(
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/inventorydb",
                "postgres",
                "Mousebear111"
            );

            // Create MyBatis environment with JDBC transactions
            Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);

            // Create MyBatis configuration and register mapper
            Configuration configuration = new Configuration(environment);
            configuration.addMapper(InventoryMapper.class);

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize MyBatis: " + e.getMessage());
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
