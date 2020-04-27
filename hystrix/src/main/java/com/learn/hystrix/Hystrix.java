package com.learn.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Observer;

public class Hystrix {
    public static void main(String[] args) {
        HystrixCommand<String> hystrixCommand = new HystrixCommand<>(() -> "hystrix") {
            @Override
            protected String run() {
                System.out.println("current thread:" + Thread.currentThread().getName());
                return "hello world";
            }
        };
        System.out.println("result is:" + hystrixCommand.execute());
        HystrixObservableCommand<String> observableCommand = new HystrixObservableCommand<>(() -> "observable") {
            @Override
            protected Observable<String> construct() {
                return Observable.just("hello world");
            }
        };
        observableCommand.toObservable().subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
            }
        });
    }
}
