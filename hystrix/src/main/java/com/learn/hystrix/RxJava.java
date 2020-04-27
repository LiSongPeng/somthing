package com.learn.hystrix;

public class RxJava {
    public static void main(String[] args) {
        MyObservable.create("dsfsdf").transform(value -> "let me be happy" + value).transform(value -> value.toUpperCase()).subscribe(value -> System.out.println("result is :" + value));
    }
}

interface MySubscriber {
    void onNext(String value);
}

interface MyOnSubscribe {
    void call(MySubscriber mySubscriber);
}

interface Transformer {
    String transform(String value);
}

class MyObservable {
    private MyOnSubscribe onSubscribe;

    private MyObservable(MyOnSubscribe onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static MyObservable create(String value) {
        return new MyObservable(mySubscriber -> {
            mySubscriber.onNext(value);
        });
    }

    public void subscribe(MySubscriber mySubscriber) {
        onSubscribe.call(mySubscriber);
    }

    public MyObservable transform(Transformer transformer) {
        MyOnSubscribe onSubscribe = mySubscriber -> MyObservable.this.subscribe(value -> {
            String transformed = transformer.transform(value);
            mySubscriber.onNext(transformed);
        });
        return new MyObservable(onSubscribe);
    }
}

