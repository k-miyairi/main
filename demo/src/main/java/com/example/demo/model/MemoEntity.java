package com.example.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemoEntity implements Serializable {

    private int id;
    private String title;
    private String content;
    private Timestamp create_time;
    private Timestamp update_time;

}
