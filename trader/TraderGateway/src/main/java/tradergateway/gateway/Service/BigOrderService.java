package tradergateway.gateway.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import tradergateway.gateway.Entity.*;
import tradergateway.gateway.OrderStorage;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BigOrderService {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderStorage orderStorage;

    public void TwapService(BigOrder bigOrder, Integer totalSeconds, Integer freqSeconds, String brokerId) {
        Long nowtime = System.currentTimeMillis();
        Long starttime = nowtime - (totalSeconds * 1000);
        Integer bunchTimes = totalSeconds / freqSeconds;
        Integer bunchNum = (bigOrder.getTotalQuantity()) / bunchTimes;
        Integer lastBunchNum = bunchNum + (bigOrder.getTotalQuantity() - bunchNum * bunchTimes);

        String history = orderService.queryBlotter(brokerId, (bigOrder.getProduct().getProductId()), "" + starttime, "" + nowtime, Brokers.get(brokerId).getUuid(), bigOrder.getTraderName());

        Gson gson = new Gson();
        List<TradeDTO> trades = gson.fromJson(history, new TypeToken<List<TradeDTO>>() {
        }.getType());
        List<Integer> prices = new ArrayList<>();
        System.err.println("start big order, qty:"+bigOrder.getTotalQuantity());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < bunchTimes; i++) {
            int temPrices = 0;
            int tempQty = 0;
            for (TradeDTO tradeDTO : trades) {
                System.out.println("Twap get history time:" + simpleDateFormat.format(tradeDTO.getTime()) + " price: " + tradeDTO.getPrice() + " quantity: " + tradeDTO.getQuantity());
                if (Long.valueOf(tradeDTO.getTime())  >= (starttime+ i * freqSeconds) && Long.valueOf(tradeDTO.getTime()) <= starttime + (i + 1) * freqSeconds) {
                    tempQty += tradeDTO.getQuantity();
                    temPrices += (tradeDTO.getQuantity()) * tradeDTO.getPrice();
                }
            }
            System.out.println("Twap bunch qty: " + tempQty);
            if (tempQty == 0) {
                prices.add(0);
            } else {
                prices.add(temPrices / tempQty);
            }
        }

        System.out.println("price list: "+gson.toJson(prices));
        Thread Twap = new Thread(new TwapThread(brokerId, bigOrder, prices, bunchTimes, bunchNum, lastBunchNum, freqSeconds));
        Twap.start();

        return;
    }

    public void VwapService(BigOrder bigOrder,Integer toralSeconds,Integer freqSeconds,String brokerId){

    }

    class TwapThread implements Runnable {

        public String brokerId;
        public BigOrder bigOrder;
        public List<Integer> prices;
        public Integer bunchTimes;
        public Integer bunchNum;
        public Integer lastBunchNum;
        public Integer freqSeconds;

        public TwapThread(String brokerId, BigOrder bigOrder, List<Integer> prices, Integer bunchTimes, Integer bunchNum, Integer lastBunchNum, Integer freqSeconds) {
            this.brokerId = brokerId;
            this.bigOrder = bigOrder;
            this.prices = prices;
            this.bunchTimes = bunchTimes;
            this.bunchNum = bunchNum;
            this.lastBunchNum = lastBunchNum;
            this.freqSeconds = freqSeconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < bunchTimes; i++) {
                if (bigOrder.getCancelFlag()) {
                    for (Order order : bigOrder.getSplitOrders()) {
                        orderService.sendCancel(brokerId, Brokers.get(brokerId).getUuid(), bigOrder.getSellOrBuy(), order.getPrice(), bigOrder.getProduct().getProductId(), order.getOrderId(), bigOrder.getTraderName());
                    }
                    break;

                }
                Integer price = prices.get(i);
                Integer qty = bunchNum;
                if (i == bunchTimes - 1) {
                    qty = lastBunchNum;
                }

                String type = "limit";
                if (price == 0) {
                    type = "market";
                }

                switch (type) {
                    case "limit":
                        Map<String, Object> res = orderService.sendBigLimitOrder(brokerId,
                                Brokers.get(brokerId).getUuid(),
                                bigOrder.getSellOrBuy(),
                                price, qty,
                                bigOrder.getProduct().getProductId(),
                                bigOrder.getTraderName());
                        Order order = new Order();
                        order.setOrderId((String) res.get("id"));
                        order.setBroker(brokerId);
                        order.setOrderType("limit");
                        order.setProduct(bigOrder.getProduct());
                        order.setSellOrBuy(bigOrder.getSellOrBuy());
                        order.setTraderName(bigOrder.getTraderName());
                        order.setTotalQuantity(qty);
                        order.setRemainingQuantity(qty);
                        order.setPrice(price);
                        order.setTime((Long) res.get("time"));
                        order.setState("waiting");
                        order.setBigOrderId(bigOrder.getId());


                        Broker broker = Brokers.get(brokerId);
                        User user = new User(bigOrder.getTraderName());
                        Product product = bigOrder.getProduct();

                        orderStorage.addOrder(broker, user, product, order);
                        bigOrder.addSplitOrder(order);
                        bigOrder.setUnsentQuantity(bigOrder.getUnsentQuantity() - qty);
                        break;

                    case "market":
                        Map<String, Object> res1 = orderService.sendBigMarket(brokerId,
                                Brokers.get(brokerId).getUuid(),
                                bigOrder.getSellOrBuy(), qty,
                                bigOrder.getProduct().getProductId(),
                                bigOrder.getTraderName());

                        Order order1 = new Order();
                        order1.setOrderId((String) res1.get("id"));
                        order1.setBroker(brokerId);        //TODO: 多个broker
                        order1.setOrderType("market");
                        order1.setProduct(bigOrder.getProduct());
                        order1.setSellOrBuy(bigOrder.getSellOrBuy());
                        order1.setTraderName(bigOrder.getTraderName());
                        order1.setTotalQuantity(qty);
                        order1.setRemainingQuantity(qty);
                        order1.setPrice(0);
                        order1.setTime((Long) res1.get("time"));
                        order1.setState("waiting");
                        order1.setBigOrderId(bigOrder.getId());

                        Broker broker1 = Brokers.get(brokerId);
                        User user1 = new User(bigOrder.getTraderName());
                        Product product1 = bigOrder.getProduct();
                        orderStorage.addOrder(broker1, user1, product1, order1);
                        bigOrder.addSplitOrder(order1);
                        bigOrder.setUnsentQuantity(bigOrder.getUnsentQuantity() - qty);
                        break;
                }
                try {
                    Thread.sleep(1000 * freqSeconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    //TEST GSON
    public static void main(String[] args) {
       class A {
           public String a=null;
           public String b="b";

           public A(){
           }
           public String getA() {
               return a;
           }

           public void setA(String a) {
               this.a = a;
           }

           public String getB() {
               return b;
           }

           public void setB(String b) {
               this.b = b;
           }
       }

        A asd=new A();

       asd.setA(null);
       asd.setB("213");
        Gson gson1 = new Gson();
        System.out.println(gson1.toJson(asd));


    }


}
