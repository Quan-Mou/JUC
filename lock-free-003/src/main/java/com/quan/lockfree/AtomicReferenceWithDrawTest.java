package com.quan.lockfree;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceWithDrawTest {



    public static void main(String[] args) {
        AccountBigDecimalService account = new AccountAtomicReference(1000000);
        List<Thread> threads = new ArrayList<>();
        for(int i =0;i<10000;i++) {
            Thread thread = new Thread(() -> {
                account.withdraw(new BigDecimal("10"));
            }, "t" + i);
            threads.add(thread);
        }

        threads.forEach(Thread::start);

        threads.forEach((thread) -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(account.getBalance());
    }
}

class AccountAtomicReference implements AccountBigDecimalService {

//    private BigDecimal balance;
    private AtomicReference<BigDecimal> balance;

    public AccountAtomicReference(int balance) {
        this.balance = new AtomicReference<>(new BigDecimal(balance));
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
       while(true){
           BigDecimal pre = this.balance.get();
           BigDecimal next = pre.subtract(amount);
           if (this.balance.compareAndSet(pre, next)) {
               break;
           }
       }
    }
}


interface AccountBigDecimalService {

    /**
     * 查看余额
     * @return
     */
    BigDecimal getBalance();

    /**
     * 取款
     * @param amount
     */
    void withdraw(BigDecimal amount);

}
