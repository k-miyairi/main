package com.example.demo.dataaccess;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataAccessApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // メモの検索
    public Map<String, Object> findMemo(int id) {

        // SELECT文
        String query = "SELECT * "
                + "FROM memo "
                + "WHERE id =?";

        // 検索実行
        Map<String, Object> memo = jdbcTemplate.queryForMap(query, id);
        return memo;
    }

    // メモの検索
    public void insertMemo(String title, String content) {

        // SELECT文
        String query = "INSERT INTO memo(title,content) "
                + "VALUES(?,?)";

        // 検索実行
        jdbcTemplate.update(query, title, content);

    }
}
