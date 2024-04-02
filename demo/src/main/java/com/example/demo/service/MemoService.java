package com.example.demo.service;

import java.sql.Timestamp;
import java.util.Map;
import com.example.demo.model.MemoEntity;
import com.example.demo.util.MemoUtil;
import com.example.demo.dataaccess.DataAccessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoService {
    @Autowired
    private DataAccessApplication daa;

    public void createMemo(String title, String content) {

        daa.insertMemo(title, content);

    }

    public MemoEntity getMemo(int key) {
        // 検索
        Map<String, Object> map = daa.findMemo(key);

        // Mapから値を取得
        int id = (Integer) map.get("id");
        String title = (String) map.get("title");
        String content = (String) map.get("content");
        Timestamp create_time = (Timestamp) map.get("create_time");
        Timestamp update_time = (Timestamp) map.get("update_time");

        String srtUCreateTime = MemoUtil.timestampToString(create_time);
        String srtUpdateTime = MemoUtil.timestampToString(update_time);

        // MemoEntityクラスに値をセット
        MemoEntity memoEntity = new MemoEntity(id, title, content, srtUCreateTime, srtUpdateTime);

        return memoEntity;
    }

}