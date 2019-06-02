package com.tradehistoryaccess.Bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(value = false)
public class Testbean implements InitializingBean {

    private static int id=0;
    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("im a independent thread "+id+" my bean : ");

    }
    class Run extends Thread{
        public void run(){
            while(true) {
                System.out.println("im a independent thread "+id+" my bean : ");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
