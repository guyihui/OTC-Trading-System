package com.tradehistoryaccess.Controller;

import com.google.gson.Gson;
import com.tradehistoryaccess.BrokerService.OrderBook.Product;
import com.tradehistoryaccess.Entity.Products;
import com.tradehistoryaccess.Entity.TradeDTO;
import com.tradehistoryaccess.Entity.Trader;
import com.tradehistoryaccess.Entity.TraderManage;
import com.tradehistoryaccess.Util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BlotterController {
    @Resource
    private TraderManage traderManage;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value = "/history",method = RequestMethod.GET)
    public String getHistory(
            @RequestParam(value = "productid") String productid,
            @RequestParam(value = "starttime",required = false,defaultValue = "0") String starttime,
            @RequestParam(value = "endtime",required = false,defaultValue = "999999999999999") String endtime,
            @RequestParam(value = "corpid",required = false,defaultValue = "magic") String corpid,
            @RequestParam(value = "tradername",required = false,defaultValue = "")String tradername){

        System.out.println("id: "+productid+" starttime "+starttime+" endtime "+endtime+"corpid: "+corpid);
        Product product= Products.get(productid);
        String period=product.getProductPeriod();

        Session s= HibernateUtils.getCurrentSession();

        Transaction tr=s.beginTransaction();
        Query q=s.createQuery("select new com.tradehistoryaccess.Entity.TradeDTO(th.id,th.broker,th.productid,th.period,th.price,th.quantity,th.initTrader,th.initCompany,th.initSide,th.compTrader,th.compCompany,th.compSide,th.timestamp) " +
                "from Tradehistory th where th.productid=:productid and th.period=:period and  (th.timestamp between :starttime and :endtime)")
                .setParameter("productid",productid)
                .setParameter("period",period)
                .setParameter("starttime",starttime)
                .setParameter("endtime",endtime);

        List<TradeDTO> trades=q.list();
        tr.commit();
        String uuid= corpid;
        if(!uuid.equals("magic")) {

            Trader trader=traderManage.getTrader(uuid);
            String corpName="";
            if(trader!=null){
                corpName=trader.getTraderCompany();
            }

            for (TradeDTO tradeDTO : trades) {

                if ((tradeDTO.getInitCompany().equals(corpName)&&tradeDTO.getInitTrader().equals(tradername))
                        || (tradeDTO.getCompCompany().equals(corpName)&&tradeDTO.getCompTrader().equals(tradername))) {
                    continue;
                }

                tradeDTO.setInitCompany("");
                tradeDTO.setInitTrader("");
                tradeDTO.setCompCompany("");
                tradeDTO.setCompTrader("");
            }
        }
        Gson gson=new Gson();
        return gson.toJson(trades);

    }
}
