package com.saber3d.task.task2.subscriber;

import com.saber3d.task.commons.ExampleItem;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * Example subscriber class. It implements {@link Flow.Subscriber}
 *
 * <p>
 * This subscriber has a {@code receivedItemsIds} as list of received item IDs.
 * We could to have this list for choosing received items which subscriber hasn't received yet.
 * </p>
 *
 * @param <T> the published item type extended by {@link ExampleItem}
 */

public class SecondTaskSubscriber<T extends ExampleItem> implements Subscriber<T> {

    List<T> elements = new ArrayList<>();
    private Subscription s;
    int onNextAmount;

    @Override
    public void onSubscribe(Subscription s) {
        System.out.println(Thread.currentThread().getName());
        this.s = s;
        s.request(2);
    }

    @Override
    public void onNext(T item) {
        elements.add(item);
        onNextAmount++;
        if (onNextAmount % 2 == 0) {
            s.request(2);
        }
    }

    @Override
    public void onError(Throwable t) {}

    @Override
    public void onComplete() {

        elements.forEach(System.out::println);

    }
}
