package com.example.demo.service;

import jakarta.servlet.http.HttpSession;

public class MemoChkLogic {

    // Title 最大文字数
    private static final int TITLE_MAX_LEN = 30;
    // Content 最大文字数
    private static final int CONTENT_MAX_LEN = 400;
    // 正常終了
    private static final String BLANK = "";
    // Error タイトル未設定
    private static final String TITLE_BLANK_MSG = "エラー：タイトルを入力してください。";
    // Error Title 最大文字数超過
    private static final String TITLE_MAX_LEN_MSG = "エラー：タイトルは30字以内で入力してください。";
    // Error Content 最大文字数超過
    private static final String CONTENT_MAX_LEN_MSG = "エラー：内容は400字以内で入力してください。";

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
