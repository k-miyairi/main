package com.example.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.MemoEntity;

public class MemoSortLogic {

    // ソート
    public List<MemoEntity> sort(List<MemoEntity> memoList, String key, String direction) {
        if (direction.equals("asc")) {
            switch (key) {
                case "id":
                    // return memoList.stream().sorted((memo1, memo2) ->
                    // memo1.getId().compareTo(memo2.getId()))
                    // .collect(Collectors.toList());
                    return memoList.stream().sorted(Comparator.comparing(MemoEntity::getId))
                            .collect(Collectors.toList());
                case "title":
                    return memoList.stream().sorted(Comparator.comparing(MemoEntity::getTitle))
                            .collect(Collectors.toList());
                case "create_time":
                    return memoList.stream().sorted(Comparator.comparing(MemoEntity::getCreate_time))
                            .collect(Collectors.toList());
                default:
                    throw new IllegalArgumentException("Invalid sort key: " + key);
            }

        } else {
            switch (key) {
                case "id":
                    return memoList.stream().sorted(Comparator.comparing(MemoEntity::getId).reversed())
                            .collect(Collectors.toList());
                case "title":
                    return memoList.stream().sorted(Comparator.comparing(MemoEntity::getTitle).reversed())
                            .collect(Collectors.toList());
                case "create_time":
                    return memoList.stream().sorted(Comparator.comparing(MemoEntity::getCreate_time).reversed())
                            .collect(Collectors.toList());
                default:
                    throw new IllegalArgumentException("Invalid sort key: " + key);
            }
        }
    }

}
