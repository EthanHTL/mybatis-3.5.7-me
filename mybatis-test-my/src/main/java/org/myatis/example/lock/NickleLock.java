package org.myatis.example.lock;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public class NickleLock {
    private static final Unsafe unsafe ;

    private static final long valueOffset ;

    static {
        try {
            Class<Unsafe> unsafeClass = Unsafe.class;
            Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            valueOffset = unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private volatile int value;

    public void lock(){
        for (;;){
            unsafe.compareAndSwapInt(this,valueOffset,0,1);
        }
    }
    public void unlock(){
        value = 0;
    }

    public static void main(String[] args) {
        System.out.println(unsafe);
    }
}
