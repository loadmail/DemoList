package com.example.fanShe;

import java.lang.reflect.Field;

/**反射获取参数并改变参数的方法
 *
 * Created by ly on 2016/10/26 16:11.
 */
//反射参数
public class FanSheTest {
    public static void main(String[] args) {
        setBoy();
        setBoyChild();
    }

    private static void setBoy() {
        try {
            Boy boy = new Boy();
            User userP = new User("parent",1);
            boy.setUser(userP);
            System.out.println("aaa----"+boy.getUser().getName());

            // TODO: 2016/10/26 反射获取参数
            Field mUser = Boy.class.getDeclaredField("user"); //viewpager中的参数mmScroller
            mUser.setAccessible(true);  //无障碍

            // TODO: 2016/10/26 改变参数
            User user = new UserChild("child",110);
            mUser.set(boy, user); //把这个viewpager的scroller设置成我想要的
            System.out.println("bbb----"+boy.getUser().getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void setBoyChild() {
        try {
            Boy boy = new Boy();
            UserChild child = new UserChild("boy_child",11);
            child.dress = "Beijing";
            boy.child = child;
            System.out.println("aaa----"+boy.child.getName());


            // TODO: 2016/10/26 反射获取参数
            Field childField = Boy.class.getDeclaredField("child");
            childField.setAccessible(true);  //无障碍

            // TODO: 2016/10/26 改变参数
            User user = new UserChild("parent",110);
            childField.set(boy, user);
            System.out.println("bbb----"+boy.child.getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
