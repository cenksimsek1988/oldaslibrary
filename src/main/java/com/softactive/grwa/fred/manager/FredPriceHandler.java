package com.softactive.grwa.fred.manager;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.softactive.core.exception.MyException;
import com.softactive.grwa.fred.AbstractFredHandler;
import com.softactive.grwa.object.Price;
import com.softactive.grwa.object.RiskFactor;

public class FredPriceHandler extends AbstractFredHandler<Price> {

	public FredPriceHandler(Map<String, Object> sharedParams) {
		super(sharedParams);
	}

	private static final long serialVersionUID = -5613352456103868158L;
	public static final String DATE = "date";
	public static final String VALUE = "value";
	private LocalDate lastDate = TOO_EARLY;
	private LocalDate firstDate = null;


	@Override
	protected Price outputComponent(JSONObject o) throws MyException{
		Price p = new Price();
		RiskFactor rf = (RiskFactor)sharedParams.get(PARAM_RISK_FACTOR);
		p.setRiskFactorId(rf.getId());
		LocalDate date = resolveValidDate(o, DATE);
		if(date.isAfter(lastDate)) {
			lastDate = date;
		}
		if(firstDate == null || date.isBefore(firstDate)) {
			firstDate = date;
		}
		p.setDataDate(sqlDate(date));
		p.setPrice(resolveValidDouble(o, VALUE));
		return p;
	}

	@Override
	public void onListSuccessfullyParsed(List<Price> list) {
		RiskFactor rf = (RiskFactor)sharedParams.get(PARAM_RISK_FACTOR);
		Date updateDate = sqlDate(LocalDate.now());
		rf.setUpdateDate(updateDate);
		rf.setEndDate(sqlDate(lastDate));
		rf.setStartDate(sqlDate(firstDate));
		String frq = (String)sharedParams.get(PARAM_FREQUENCY_ID);
		rf.setFrequencyCode(frq);
		sharedParams.put(PARAM_RISK_FACTOR, rf);
		super.onListSuccessfullyParsed(list);
	}
	@Override
	public void onError() {
		RiskFactor rf = (RiskFactor)sharedParams.get(PARAM_RISK_FACTOR);
		Date updateDate = sqlDate(LocalDate.now());
		rf.setUpdateDate(updateDate);
		String frq = (String)sharedParams.get(PARAM_FREQUENCY_ID);
		rf.setFrequencyCode(frq);
		sharedParams.put(PARAM_RISK_FACTOR, rf);
		super.onError();
	}

	
	@Override
	protected JSONArray inputBody(JSONObject r) throws MyException {
		try {
			return r.getJSONArray(OBSERVATIONS);
		} catch (JSONException e) {
			throw new MyException("Error while parsing array: " + e.getMessage());
		}
		
	}
}
