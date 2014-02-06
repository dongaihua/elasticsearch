package org.elasticsearch.payment;

import java.util.Map;

/**
 * Created by jackiedong168 on 14-1-20.
 */
public class IPay extends Gateway {
    public static Map purchase(Map state) {
        Map s1 = getAdditonalParametersFromReq(state);
        Map s2 = getParametersFromDB(s1);
        Map s3 = processPurchase(s2);
        Map s4 = processPurchase(s3);
        return state;
    }

    public static Map getAdditonalParametersFromReq(Map state) {
        return state;
    }

    public static Map getParametersFromDB(Map state) {
        return state;
    }

    public static Map processPurchase(Map state) {
        return state;
    }

    public static Map processPurchaseResponse(Map state) {
        return state;
    }

}
