package com.synechisveltiosi.apis.app365.accounts.helpers;

import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;
import org.apache.commons.lang3.StringUtils;

public final class AccountHelper {

    public static String formatIdCard(String idCard, CrmConfig crmConfig) {
        if (crmConfig != null && !StringUtils.isBlank(idCard) && crmConfig.getIdCardRequireDashes())
            return idCard.replaceFirst("([0-9]{3})([0-9]{7})([0-9])", "$1-$2-$3");

        return idCard;
    }
}
