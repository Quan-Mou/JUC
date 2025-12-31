package com.quan.lockfree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeWithDrawTest {

    public static void main(String[] args) {
        AccountUnSafe account = new AccountUnSafe(1000000);
        List<Thread> threads = new ArrayList<>();
        for(int i =0;i<10000;i++) {
            Thread thread = new Thread(() -> {
                account.withdraw(10);
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

class AccountUnSafe implements AccountService {

    private AtomicInteger balance = new AtomicInteger();

    public AccountUnSafe(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public int getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(int amount) {
        while(true){
            //        this.balance -= new AtomicInteger(amount);
            int pre = balance.get();
            int next = pre-amount;
            // 把pre，改成next，如果当前值是compareAndSet(expect,update),中的expect，就它原子的更新为update这个值，否则就什么也不做。无论是否成功都会返回一个布尔值
            if (this.balance.compareAndSet(pre, next)) {
                break; // 更新成功，退出，否则一直尝试。
            }
        }

    }
}


interface AccountService {

    /**
     * 查看余额
     * @return
     */
    int getBalance();

    /**
     * 取款
     * @param amount
     */
    void withdraw(int amount);

}
