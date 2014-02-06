package org.elasticsearch.test.unit.common;

import org.elasticsearch.common.io.FastByteArrayOutputStream;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentGenerator;
import org.elasticsearch.common.xcontent.XContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackiedong168 on 14-1-20.
 */
public class CommonTests {

    @Test
    public void testUpdateObject() throws Exception {
        Map<String, String> map = new HashMap<String, String>(30);
        Map out;
        map.put("a", "b");
        out = update(map);
        System.out.print(out);


    }


    Map update(Map input) {
        input.put("newKey", "newValue");
        System.out.print(input);
        return input;
    }


}
