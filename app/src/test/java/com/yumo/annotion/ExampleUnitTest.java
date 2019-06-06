package com.yumo.annotion;

import com.yumo.annotation.YmClassTest;
import com.yumo.annotion.runtimeannotion.TestClassAnnotion;

import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }



    @Test
    public void testClassAnnotion(){
        TestClassAnnotion testClassAnnotion = new TestClassAnnotion();
        Annotation[] annotations = testClassAnnotion.getClass().getAnnotations();
        if (annotations == null || annotations.length == 0){
            System.out.println("没有注解");
        }else{
            for (Annotation annotation : annotations){
                if (annotation instanceof YmClassTest){
                    YmClassTest ymClassTest = (YmClassTest)annotation;
                    System.out.print(ymClassTest.name());
                }
            }
        }
    }

    @Test
    public void testClassAnnotion1(){
        TestClassAnnotion testClassAnnotion = new TestClassAnnotion();
        YmClassTest annotation = testClassAnnotion.getClass().getAnnotation(YmClassTest.class);
        if (annotation == null){
            System.out.println("没有注解");

        }else{
            System.out.print(annotation.name());
        }
    }
}