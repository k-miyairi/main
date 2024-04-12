package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.MemoEntity;

public class MemoSearchLogic {

    // キーワード検索
    public List<MemoEntity> search(List<MemoEntity> memoList, String keyword) {
        if (keyword.isEmpty()) {
            return memoList;
        } else {
            return memoList.stream()
                    .filter(memo -> memo.getTitle().contains(keyword) || memo.getContent().contains(keyword))
                    .collect(Collectors.toList());
        }
    }

}
