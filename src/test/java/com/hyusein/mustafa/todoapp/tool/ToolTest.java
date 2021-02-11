package com.hyusein.mustafa.todoapp.tool;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToolTest {

    public Long val1;
    public String val2;
    public List val3;

    @Test
    void remediate() {
        ToolTest str1 = new ToolTest();
        str1.val1 = 1L;
        str1.val2 = "val2 test";
        str1.val3 = new ArrayList();
        ToolTest str2 = new ToolTest();

        Tool.remediate(str2, str1);

        assertEquals(str1.val1, str2.val1);
        assertEquals(str1.val2, str2.val2);
        assertEquals(str1.val3, str2.val3);
    }
}