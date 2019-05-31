package com.tradehistoryaccess.Controller;

import com.tradehistoryaccess.Util.HibernateUtils;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlotterController {
    @RequestMapping(value = "/history",method = RequestMethod.GET)
    public String getHistory(
            @RequestParam(value = "productid") String productid,@RequestParam(value = "period") String period,@RequestParam(value = "starttime",required = false,defaultValue = "0") String starttime,@RequestParam(value = "endtime",required = false,defaultValue = "999999999999999") String endtime,@RequestParam(value = "name",required = false,defaultValue = "") String name){

        Session s= HibernateUtils.getCurrentSession();



    }
}
