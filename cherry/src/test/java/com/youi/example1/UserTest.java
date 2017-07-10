package com.youi.example1;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by wkq on 16/7/15.
 */
@Service("userTest")
public class UserTest {

    public List<String> getUsers(String initiator)
    {
        List<String> list=new ArrayList<String>();

        list.add("wkq");
        list.add("eric");
        return list;

    }
}
