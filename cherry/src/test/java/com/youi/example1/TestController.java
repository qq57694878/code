package com.youi.example1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jinliang on 2016/7/13.
 */
@RestController
public class TestController {
    private ObjectMapper objectMapper = new ObjectMapper();

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/restfultest")
    public Pojo greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Pojo(counter.incrementAndGet(),"hello,"+name);
    }

    @RequestMapping("/restfultest1/{name}")
    public Pojo restfultest1(@PathVariable String name) {
        return new Pojo(counter.incrementAndGet(),"hello,"+name);
    }
    @RequestMapping("/restfultest2")
    public Pojo restfultest2(@RequestBody String pojo) throws IOException {
        Pojo p =  objectMapper.readValue(pojo,Pojo.class);
        return p;
    }
    @RequestMapping(value="/restfultest3")
    public Pojo restfultest3(@RequestBody Pojo pojo) throws IOException {
        return pojo;
    }


}
