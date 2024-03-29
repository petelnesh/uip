package org.uipcredits.mobilebanking.models

import java.util.ArrayList

/**
 * @author Vishwajeet
 * @since 16/8/16.
 */
data class ChargeListResponse (
    var pageItems: List<Charge> = ArrayList()
)
