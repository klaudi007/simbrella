package com.crypt;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeNonBlockingStack <E> {

        AtomicReference<Node<E>> top = new AtomicReference<>();

        private final Lock lock;
        private final Condition isEmpty;
        private final Condition isFull;


        public ThreadSafeNonBlockingStack(){
            lock = new ReentrantLock();
            isEmpty = lock.newCondition();
            isFull = lock.newCondition();
        }


        private volatile boolean completed = false;

        public void setCompleted(){
            completed = true;
//            isFull.signal();
        }

        public boolean isCompleted(){
            return completed;
        }

        public void push(E item) {

//            lock.lock();

            Node<E> newHead = new Node<E>(item);
            Node<E> oldHead;
            do {
                oldHead = top.get();
                newHead.next = oldHead;
            } while (!top.compareAndSet(oldHead, newHead));
        }

        public E pop() {
            Node<E> oldHead;
            Node<E> newHead;
            do {
                oldHead = top.get();
                if (oldHead == null)
                    return null;
                newHead = oldHead.next;
            } while (!top.compareAndSet(oldHead, newHead));
            return oldHead.item;
        }

        private static class Node <E> {
            public final E item;
            public Node<E> next;

            public Node(E item) {
                this.item = item;
            }
        }

}
