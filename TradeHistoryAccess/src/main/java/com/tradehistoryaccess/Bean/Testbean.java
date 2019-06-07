package com.tradehistoryaccess.Bean;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public static void main(String []args){
        Gson gson=new Gson();
        List<Map<Integer,List<TestDao>>> list=new ArrayList<>();
        Map<Integer,List<TestDao>>tempmap=new HashMap<>();
        List<TestDao> templist=new ArrayList<>();
        templist.add(new TestDao("test1",10));
        templist.add(new TestDao("test2",10));

        List<TestDao> templist1=new ArrayList<>();
        templist1.add(new TestDao("test3",10));
        templist1.add(new TestDao("test4",10));

        tempmap.put(10,templist);

        list.add(tempmap);


        list.add(new HashMap<>());
        Map<Integer, List<TestDao>> map=new HashMap<>();
        List<TestDao> list1=new ArrayList<>();
        list1.add(new TestDao("test1",10));
        list1.add(new TestDao("test2",15));

        List<TestDao> list2=new ArrayList<>();
        list2.add(new TestDao("test3",10));
        list2.add(new TestDao("test4",15));

        map.put(20,list1);
        map.put(10,list2);


        JsonObject jsonObject=new JsonObject();
        System.out.println(gson.toJson(map));
    }
}
