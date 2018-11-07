package com.softactive.grwa.manager;

import java.util.HashMap;
import java.util.Map;

import com.softactive.grwa.object.GrwaConstants;
import com.softactive.grwa.object.Indicator;
import com.softactive.grwa.object.Region;
import com.softactive.grwa.object.RiskFactor;

public interface PriceRequestHelper extends GrwaConstants{
	
	public default Map<String, Object> initSharedParams(RiskFactor rf, Region r, Indicator i){
		Map<String, Object> sharedParams = new HashMap<String, Object>();
		System.out.println("risk factor id= " + rf.getId());
		sharedParams.put(PARAM_RISK_FACTOR, rf);
		sharedParams.put(PARAM_REGION, r);
		sharedParams.put(PARAM_INDICATOR, i);
		return sharedParams;
	}

}
