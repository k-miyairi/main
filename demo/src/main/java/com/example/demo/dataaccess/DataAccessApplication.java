package com.example.demo.dataaccess;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataAccessApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> findMemo(int id) {

        // SELECT文
        String query = "SELECT * "
                + "FROM memo "
                + "WHERE id =?";

        // 検索実行
        Map<String, Object> memo = jdbcTemplate.queryForMap(query, id);
        return memo;
    }
}
