package com.tradehistoryaccess.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static final Configuration cfg;
    private  static final SessionFactory sessionFactory;

    static {
        cfg=new Configuration();
        cfg.configure();
        sessionFactory=cfg.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public static Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
