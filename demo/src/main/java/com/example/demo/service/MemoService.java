package com.example.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.model.MemoEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class MemoService {

    private static final int TITLE_MAX_LEN = 30;

    private static final int CONTENT_MAX_LEN = 400;

    private static final String BLANK = "";

    private static final String TITLE_BLANK_MSG = "エラー：タイトルを入力してください。";

    private static final String TITLE_MAX_LEN_MSG = "エラー：タイトルは30字以内で入力してください。";

    private static final String CONTENT_MAX_LEN_MSG = "エラー：内容は400字以内で入力してください。";

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

    public String chkTitle(String str) {
        if (str.isEmpty()) {
            return TITLE_BLANK_MSG;
        } else {
            if (str.length() > TITLE_MAX_LEN) {
                return TITLE_MAX_LEN_MSG;
            } else {
                return BLANK;
            }
        }
    }

    public String chkContent(String str) {
        if (str.length() > CONTENT_MAX_LEN) {
            return CONTENT_MAX_LEN_MSG;
        } else {
            return BLANK;
        }
    }

    public Boolean chkToken(HttpSession session, String token) {
        String sessionToken = (String) session.getAttribute("token");
        if (sessionToken != null && sessionToken.equals(token)) {
            // トークンが一致した場合
            return true;
        } else {
            // トークンが一致しない場合
            return false;
        }
    }
}