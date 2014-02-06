package org.elasticsearch.payment;

import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jackiedong168 on 14-1-20.
 */
public class Gateway {

    private static Sql2o sql2o;

    static{
        sql2o = new Sql2o("jdbc:postgresql://localhost:5432/postgres", "ploutos", "ploutos");
    }


    public static Map purchase(Map state) throws Exception {
        Map newState = checkCreditcard(state);
        Map newState2 = checkAlreadyPaid(newState);
        String paymentMethodId = (String) state.get("payment-method-id");
        if (paymentMethodId == "1003") {
            return IPay.purchase(newState2);
        }
        return state;
    }

    static Map checkCreditcard(Map state) throws Exception {
        Map creditcard = (HashMap) state.get("creditcard");
        String cvv = (String) creditcard.get("cvv");
        if (cvv == null) throw new Exception("Creditcard is invalid");
        return state;
    }

    static Map checkAlreadyPaid(Map state) throws Exception {
        return state;
    }




























}


