package com.tradehistoryaccess.Controller;

import com.google.gson.Gson;
import com.tradehistoryaccess.Entity.TradeDTO;
import com.tradehistoryaccess.Util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlotterController {
    @CrossOrigin(origins = "*",maxAge = 3600)
    @RequestMapping(value = "/history",method = RequestMethod.GET)
    public String getHistory(
            @RequestParam(value = "productid") String productid,@RequestParam(value = "period") String period,@RequestParam(value = "starttime",required = false,defaultValue = "0") String starttime,@RequestParam(value = "endtime",required = false,defaultValue = "999999999999999") String endtime,@RequestParam(value = "corpname",required = false,defaultValue = "magic") String corpName){

        System.out.println("id: "+productid+" period "+period+" starttime "+starttime+" endtime "+endtime);
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
        String corpname= corpName;
        if(!corpname.equals("magic")) {


            for (TradeDTO tradeDTO : trades) {
                if (tradeDTO.getInitTrader().equals(corpName) || tradeDTO.getCompCompany().equals(corpName)) {
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
