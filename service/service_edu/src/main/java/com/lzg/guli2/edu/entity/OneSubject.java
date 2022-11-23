package com.lzg.guli2.edu.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();

    public void addTwoSubjects(TwoSubject twoSubject) {
        this.children.add(twoSubject);
    }


}
