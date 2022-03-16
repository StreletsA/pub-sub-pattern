package com.saber3d.task.task1.subscriber;

import com.saber3d.task.commons.ExampleItem;

import java.util.ArrayList;
import java.util.concurrent.Flow;

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

public class ExampleSubscriber<T extends ExampleItem> implements Flow.Subscriber<T> {

    private Flow.Subscription subscription;

    private final ArrayList<Integer> receivedItemsIds;
    private final int subscriberId;

    public ExampleSubscriber(int subscriberId){

        receivedItemsIds = new ArrayList<>();

        this.subscriberId = subscriberId;

    }

    private void process(T item){

        // do with item something

        System.out.println(subscriberId + " : " + item);

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

        this.subscription = subscription;
        subscription.request(1);

    }

    @Override
    public void onNext(T item) {

        synchronized (receivedItemsIds) {
            if (!receivedItemsIds.contains(item.getId())) {

                receivedItemsIds.add(item.getId());
                process(item);

            }
        }

        subscription.request(1);

    }

    @Override
    public void onError(Throwable throwable) {

        throwable.printStackTrace();

    }

    @Override
    public void onComplete() {

        System.out.println("Done");

    }
}
