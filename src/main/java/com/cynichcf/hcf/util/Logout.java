package com.cynichcf.hcf.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Logout {

    private int taskId;
    private long logoutTime;
}
