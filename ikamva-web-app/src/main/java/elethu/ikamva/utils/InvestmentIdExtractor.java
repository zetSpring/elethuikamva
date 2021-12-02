package elethu.ikamva.utils;

import org.apache.commons.lang3.StringUtils;

public class InvestmentIdExtractor {

    public static String ExtractInvestID(String paymentRef){
        String investmentId = StringUtils.normalizeSpace(paymentRef);
        if(StringUtils.containsWhitespace(investmentId)){
            investmentId = investmentId.substring(investmentId.lastIndexOf(" ") + 1);
        }

        return investmentId;
    }
}
