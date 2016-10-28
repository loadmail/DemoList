package com.example.li.demo.rxjavaTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.li.demo.R;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
    }

    private void myCode() {

        // TODO: 2016/10/18    如果一个Observable没有任何的的Subscriber，那么这个Observable是不会发出任何事件的。

        // TODO: 2016/10/18  1. 创建一个Observable(被观察者)对象，直接调用Observable.create即可
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> sub) {
                // TODO: 2016/10/18   2.这里定义的Observable对象仅仅发出一个Hello World字符串，然后就结束了。
                sub.onNext("Hello, world!");
                sub.onCompleted();
            }
        });

// TODO: 2016/10/18 3.创建一个观察者对象
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        // TODO: 2016/10/18 4. 订阅关系
        myObservable.subscribe(mySubscriber);

        // TODO: 2016/10/18  说明:  一旦mySubscriber订阅了myObservable，myObservable就是调用mySubscriber对象的onNext和onComplete方法，mySubscriber就会打印出Hello World！


        // TODO: 2016/10/18 更简洁的代码

        // TODO: 2016/10/18 简化Observable对象的创建过程
        //todo  Observable.just就是用来创建只发出一个事件就结束的Observable对象
        Observable<String> observable = Observable.just("Hello, world!");


        // TODO: 2016/10/18  简化Subscriber
        // TODO: 2016/10/18 并不关心OnComplete和OnError，我们只需要在onNext的时候做一些处理，这时候就可以使用Action1类

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };



        // TODO: 2016/10/18 重载版本 myObservable.subscribe(onNextAction, onErrorAction, onCompleteAction);
        // TODO: 2016/10/18 接受三个Action1类型的参数，分别对应OnNext，OnComplete， OnError函数
        myObservable.subscribe(
                onNextAction,
                throwable -> Log.e("打印错误","onError"+throwable.getMessage().toString()),
                () -> Log.e("已经","onCompleted"));


// TODO: 2016/10/18 最终版本

        Observable.just("Hello, world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });

        // TODO: 2016/10/18 重要:  其实认清了对象就好办了
        // TODO: 2016/10/18 中心就是 Observable对象.subscribe(Subscriber对象)  肯定少不了.subscribe
        // TODO: 2016/10/18  Observable对象创建: 1.Observable.create  2.Observable.just(T)
        // TODO: 2016/10/18  Subscriber对象创建: 1. new Subscriber<Object> 2. new Action1<Object>


// TODO: 2016/10/18  //使用map操作来完成类型转换
//TODO   String >"Hello, world!"转换成 Integer >s.hashCode()
        // TODO: 2016/10/18  用到的方法是map(new Func1<>())
        Observable.just("Hello, world!")
                .map(s -> {
                    return s.hashCode() + 11; //s.hashCode() 是一个Integer对象
                })
                .subscribe(i -> {
                    System.out.println(Integer.toString(i));
                });
        //简化写法  直接操作数据时的写法  获取abc的哈希值然后打印出来
        observable.just("abc").map(String::hashCode).subscribe(System.err::println);
    }


    private void myCode2() {
        // TODO: 2016/10/18 map操作符的使用  线程调度
        // TODO: 2016/10/18 线程调度只有subscribeOn（）和observeOn（）两个方法

        // TODO: 2016/10/18 subscribeOn（）只作用于被观察者创建阶段,指示Observable的创建线程。只能指定一次
        // TODO: 2016/10/18 observeOn（）可指定多次，每次指定完都在下一步生效。指定在事件传递（加工变换 map）和最终被处理（观察者 subscriber）的发生的线程。
        Observable.just("abc").  //创建Observable对象
                subscribeOn(Schedulers.newThread()).//Observable对象在子线程创建 subscribeOn
                observeOn(Schedulers.io()).  //将接下来执行的线程环境指定为io线程,进行耗时操作 observeOn下一步执行
                map(s -> s.hashCode() + 11).  //类型转换 String转integer 耗时操作
                observeOn(AndroidSchedulers.mainThread()).  //在主线程运行  observeOn 下一步执行
                subscribe(integer -> {                       //订阅
            android.util.Log.e("操作integer", integer.toString());  //操作integer 在主线程执行
        });

        Observable.just("bcd").
                subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).

                flatMap(new Func1<String, Observable<File>>() {
                    @Override
                    public Observable<File> call(String s) {
                        if (s.equals("bcd")) {
                            File f = new File("abc");
                            return listFiles(f);
                        }
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(File::toString);
    }

    private Observable<File> listFiles(File f) {
        if (f.isDirectory()) {
            return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    return listFiles(f);
                }
            });
        } else {
            return Observable.just(f);
        }
    }

}
