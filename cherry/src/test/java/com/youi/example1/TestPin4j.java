package com.youi.example1;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by jinliang on 2016/9/21.
 */
public class TestPin4j {
    public  static void main(String args[]) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat format =  new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
         String s =   PinyinHelper.toHanYuPinyinString("你好",format,"",false);
        System.out.println(s);
    }
}
