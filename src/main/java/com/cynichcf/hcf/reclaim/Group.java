package com.cynichcf.hcf.reclaim;

import lombok.Data;

import java.util.List;

@Data
public class Group {

    private String name;
    private List<String> commands;
}
