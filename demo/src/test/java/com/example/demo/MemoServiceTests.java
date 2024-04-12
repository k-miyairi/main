package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import com.example.demo.model.MemoEntity;
import com.example.demo.service.MemoService;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
public class MemoServiceTests {

    @Autowired
    private MemoService memoService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Test
    public void testSortIdAsc() {
        String key = "id";
        String direction = "asc";
        List<MemoEntity> memoList = new ArrayList<>();
        memoList = memoService.sort(memoList(), key, direction);

        assertEquals(memoList(), memoList);
    }

    @Test
    public void testSortIdDesc() {
        String key = "id";
        String direction = "desc";
        List<MemoEntity> memoList = new ArrayList<>();
        memoList = memoService.sort(memoList(), key, direction);

        assertEquals(memoList_desc(), memoList);
    }

    @Test
    public void testSortTitleAsc() {
        String key = "title";
        String direction = "asc";
        List<MemoEntity> memoList = new ArrayList<>();
        memoList = memoService.sort(memoList(), key, direction);

        assertEquals(memoList(), memoList);
    }

    @Test
    public void testSortTitleDesc() {
        String key = "title";
        String direction = "desc";
        List<MemoEntity> memoList = new ArrayList<>();
        memoList = memoService.sort(memoList(), key, direction);

        assertEquals(memoList_desc(), memoList);
    }

    @Test
    public void testSortCreatTimeAsc() {
        String key = "create_time";
        String direction = "asc";
        List<MemoEntity> memoList = new ArrayList<>();
        memoList = memoService.sort(memoList(), key, direction);

        assertEquals(memoList(), memoList);
    }

    @Test
    public void testSortCreatTimeDesc() {
        String key = "create_time";
        String direction = "desc";
        List<MemoEntity> memoList = new ArrayList<>();
        memoList = memoService.sort(memoList(), key, direction);

        assertEquals(memoList_desc(), memoList);
    }

    @Test
    public void testSortErrorAsc() {
        String key = "content";
        String direction = "asc";
        try {
            memoService.sort(memoList(), key, direction);
            fail("error");
        } catch (IllegalArgumentException e) {
            // 期待通りの例外がスローされた場合、テストは成功
            assertEquals("Invalid sort key: " + key, e.getMessage());
        }
    }

    @Test
    public void testSortErrorDesc() {
        String key = "content";
        String direction = "desc";
        try {
            memoService.sort(memoList(), key, direction);
            fail("error");
        } catch (IllegalArgumentException e) {
            // 期待通りの例外がスローされた場合、テストは成功
            assertEquals("Invalid sort key: " + key, e.getMessage());
        }
    }

    @Test
    public void testChkTitle() {
        String title = "title";
        String result = memoService.chkTitle(title);

        assertEquals("", result);
    }

    @Test
    public void testChkTitleBlank() {
        String title = "";
        String result = memoService.chkTitle(title);

        assertEquals("エラー：タイトルを入力してください。", result);
    }

    @Test
    public void testChkTitleMaxLen() {
        String title = "aaaaaaaaaabbbbbbbbbbccccccccccd";
        String result = memoService.chkTitle(title);

        assertEquals("エラー：タイトルは30字以内で入力してください。", result);
    }

    @Test
    public void testChkContent() {
        String content = "content";
        String result = memoService.chkContent(content);

        assertEquals("", result);
    }

    @Test
    public void testChkContentMaxLen() {
        String content = "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee"
                + "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee"
                + "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee"
                + "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee"
                + "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee"
                + "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee"
                + "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee"
                + "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeee"
                + "a";
        String result = memoService.chkContent(content);

        assertEquals("エラー：内容は400字以内で入力してください。", result);
    }

    @Test
    public void testSearch() {
        String keyword = "title2";
        List<MemoEntity> memoList = new ArrayList<>();
        memoList = memoService.search(memoList(), keyword);

        assertEquals(memoList_search(), memoList);
    }

    @Test
    public void testSearchBlank() {
        String keyword = "";
        List<MemoEntity> memoList = new ArrayList<>();
        memoList = memoService.search(memoList(), keyword);

        assertEquals(memoList(), memoList);
    }

    @Test
    public void testChkTokenTrue() {
        String token = "ok";
        Mockito.when(session.getAttribute("token")).thenReturn("ok");
        Boolean result = memoService.chkToken(session, token);

        assertEquals(true, result);
    }

    @Test
    public void testChkTokenFalse() {
        String token = "ng";
        Mockito.when(session.getAttribute("token")).thenReturn("ok");
        Boolean result = memoService.chkToken(session, token);

        assertEquals(false, result);
    }

    public List<MemoEntity> memoList() {
        List<MemoEntity> memoList = new ArrayList<>();

        MemoEntity memo1 = new MemoEntity();
        memo1.setId(1);
        memo1.setTitle("title1");
        memo1.setContent("content");
        memo1.setCreate_time(Timestamp.valueOf("2024-04-10 09:00:00.01"));
        memoList.add(memo1);

        MemoEntity memo2 = new MemoEntity();
        memo2.setId(2);
        memo2.setTitle("title2");
        memo2.setContent("content");
        memo2.setCreate_time(Timestamp.valueOf("2024-04-11 09:00:00.01"));
        memoList.add(memo2);

        return memoList;
    }

    public List<MemoEntity> memoList_desc() {
        List<MemoEntity> memoList = new ArrayList<>();

        MemoEntity memo1 = new MemoEntity();
        memo1.setId(2);
        memo1.setTitle("title2");
        memo1.setContent("content");
        memo1.setCreate_time(Timestamp.valueOf("2024-04-11 09:00:00.01"));
        memoList.add(memo1);

        MemoEntity memo2 = new MemoEntity();
        memo2.setId(1);
        memo2.setTitle("title1");
        memo2.setContent("content");
        memo2.setCreate_time(Timestamp.valueOf("2024-04-10 09:00:00.01"));
        memoList.add(memo2);

        return memoList;
    }

    public List<MemoEntity> memoList_search() {
        List<MemoEntity> memoList = new ArrayList<>();

        MemoEntity memo1 = new MemoEntity();
        memo1.setId(2);
        memo1.setTitle("title2");
        memo1.setContent("content");
        memo1.setCreate_time(Timestamp.valueOf("2024-04-11 09:00:00.01"));
        memoList.add(memo1);

        return memoList;
    }

}