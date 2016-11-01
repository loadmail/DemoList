package com.example.li.demo.testTouch;

import android.app.Activity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提示这个方法被哪个Activity调用
 * 
 * @author Administrator
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//annotate 注解
public @interface AnnoCallback {
	/**
	 * 回调对象名称
	 * 
	 * @return
	 */
	public Class<?> name() default Activity.class;
}
