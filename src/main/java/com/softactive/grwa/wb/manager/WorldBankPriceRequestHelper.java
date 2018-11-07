package com.softactive.grwa.wb.manager;

import java.util.Calendar;
import java.util.Map;

import com.softactive.grwa.manager.PriceRequestHelper;
import com.softactive.grwa.object.Indicator;
import com.softactive.grwa.object.Price;
import com.softactive.grwa.object.Region;
import com.softactive.grwa.object.RiskFactor;
import com.softactive.grwa.wb.AbstractWorldBankRequester;

import okhttp3.HttpUrl;

public class WorldBankPriceRequestHelper extends AbstractWorldBankRequester<Price> implements PriceRequestHelper {
	public static final String WORLD_BANK_PRICE_URL = "http://api.worldbank.org/v2";
	public static final String COUNTRIES = "/countries/";
	public static final String INDICATORS = "/indicators/";
	
	private static final long serialVersionUID = -5415904908399856829L;
	private static final String DATE = "date";
	
	public WorldBankPriceRequestHelper(RiskFactor rf, Region r, Indicator i) {
		sharedParams = initSharedParams(rf,r,i);
	}

	@Override
	protected HttpUrl onSetParameters(HttpUrl.Builder builder, Map<String, Object> sharedParams) {
		RiskFactor rf = (RiskFactor)sharedParams.get(PARAM_RISK_FACTOR);
		if(rf.getUpdateDate()!=null) {
			builder.addQueryParameter(DATE, getDateInterval(rf));
		}
		return super.onSetParameters(builder, sharedParams);
	}

	private String getDateInterval(RiskFactor rf) {
		Calendar now = Calendar.getInstance();
		int yearEnd = now.get(Calendar.YEAR);
		Calendar start = Calendar.getInstance();
		start.setTime(rf.getUpdateDate());
		int yearStart;
		switch(rf.getFrequencyCode()) {
		case FREQUENCY_ANNUAL:
			start.add(Calendar.YEAR, -1);
			yearStart = start.get(Calendar.YEAR);
			return yearStart + ":" + yearEnd;
		case FREQUENCY_QUARTERLY:
			start.add(Calendar.MONTH, -3);
			yearStart = start.get(Calendar.YEAR);
			return yearStart + "Q" + getQuarter(start) + ":" +
			yearEnd + "Q" + getQuarter(now);
		case FREQUENCY_MONTHLY:
			start.add(Calendar.MONTH, -1);
			yearStart = start.getActualMaximum(Calendar.YEAR);
			return yearStart + getMonth(start) + ":" + 
			yearEnd + getMonth(now);
		default: return rf.getFrequencyCode();
		}
	}

	private int getQuarter(Calendar c) {
		int pseudoQuarter = c.get(Calendar.MONTH)/3;
		return pseudoQuarter==0 ? 4:pseudoQuarter;
	}

	private String getMonth(Calendar c) {
		int month = c.get(Calendar.MONTH);
		int pseudoMonth = month + 100;
		return "M" + String.valueOf(pseudoMonth).substring(1);
	}

	@Override
	protected String onSetUrl(Map<String, Object> sharedParams) {
		RiskFactor rf = (RiskFactor) sharedParams.get(PARAM_RISK_FACTOR);
		if(rf==null) {
			return null;
		}
		Region r = (Region) sharedParams.get(PARAM_REGION);
		Indicator i = (Indicator) sharedParams.get(PARAM_INDICATOR);
		String url = WORLD_BANK_PRICE_URL;
		url += COUNTRIES + r.getIsoCode();
		url += INDICATORS + i.getApiCode();
		return url;
	}
}
