package com.example.demo.service;

import java.util.Comparator;
import java.util.List;

import com.example.demo.model.MemoEntity;
import com.example.demo.dataaccess.DataAccessApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoService {

    @Autowired
    private DataAccessApplication daa;

    public MemoEntity selectMemo(int id) {
        // 参照
        MemoEntity memoEntity = daa.findMemo(id);
        return memoEntity;
    }

    public List<MemoEntity> getAllMemo() {
        // 検索
        List<MemoEntity> listMemo = daa.findMemoAll();

        return listMemo;
    }

    public void createMemo(String title, String content) {
        // 登録
        daa.insertMemo(title, content);
    }

    public void deleteMemo(int id) {
        // 削除
        daa.deleteMemo(id);
    }

    public Comparator<MemoEntity> sort(String key, String direction) {

        if (direction.equals("asc")) {
            switch (key) {
                case "id":
                    return Comparator.comparing(MemoEntity::getId);
                case "title":
                    return Comparator.comparing(MemoEntity::getTitle);
                case "create_time":
                    return Comparator.comparing(MemoEntity::getCreate_time);
                default:
                    throw new IllegalArgumentException("Invalid sort key: " + key);
            }
        } else {
            switch (key) {
                case "id":
                    return Comparator.comparing(MemoEntity::getId).reversed();
                case "title":
                    return Comparator.comparing(MemoEntity::getTitle).reversed();
                case "create_time":
                    return Comparator.comparing(MemoEntity::getCreate_time).reversed();
                default:
                    throw new IllegalArgumentException("Invalid sort key: " + key);
            }
        }
    }

}