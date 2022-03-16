package com.saber3d.task.task1.publisher;

import com.saber3d.task.commons.ExampleItem;

import java.util.ArrayList;
import java.util.concurrent.Flow;

/**
 * Example publisher class. It implements {@link Flow.Publisher}
 *
 * <p>
 * This publisher has a {@code storage} as list of submitted items.
 * We could to have a storage for continuous items sending. Publisher send all items from the storage to subscribers
 * all time thereby we don't worry about new consumers or slow consumers. All consumers receive all items from storage.
 * </p>
 *
 * @param <T> the published item type extended by {@link ExampleItem}
 */

/*
Traditionally, messaging-oriented middleware products have several methods for handling slow consumers:

    1) Blocking or slowing down the producer
    My choice -> 2) Spooling messages to disk and replaying at a later time
    3) Discarding messages for the slow consumer
    4) Dropping the slow consumer

 */

public class ExamplePublisher<T extends ExampleItem> implements Flow.Publisher<T> {

    private ArrayList<T> storage;

    public ExamplePublisher(){

        storage = new ArrayList<>();

    }

    public void submit(T item){

        item.setId(storage.size() + 1);

        if (!storage.contains(item)) {
            System.out.println("Item added: " + item);
            storage.add(item);
        }

    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {

        new Thread(() -> {

            while(storage != null && subscriber != null){

                for(T item : storage){
                    subscriber.onSubscribe(new Flow.Subscription() {
                        @Override
                        public void request(long n) {

                        }

                        @Override
                        public void cancel() {

                        }
                    });
                    subscriber.onNext(item);

                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();

    }

}
