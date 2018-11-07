package com.softactive.grwa.wb.manager;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.json.JSONObject;

import com.softactive.core.exception.MyException;
import com.softactive.grwa.object.Price;
import com.softactive.grwa.object.RiskFactor;
import com.softactive.grwa.wb.AbstractWorldBankHandler;

public class WorldBankPriceHandler extends AbstractWorldBankHandler<Price>{
	private LocalDate lastDate = TOO_EARLY;
	private LocalDate firstDate = null;

	private static final long serialVersionUID = 7934081590980387905L;
	public static final String VALUE = "value";
	public static final String DATE = "date";

	public WorldBankPriceHandler(Map<String, Object> sharedParams) {
		super(sharedParams);
	}
	
	@Override
	protected Price outputComponent(JSONObject o) throws MyException {
		Price p = new Price();
		RiskFactor rf = (RiskFactor)sharedParams.get(PARAM_RISK_FACTOR);
		p.setRiskFactorId(rf.getId());
		String dateString = resolveValidString(o, DATE, null);
		String frq = getFrequencyId(dateString);
		sharedParams.put(PARAM_FREQUENCY_ID, frq);
		LocalDate date = getDate(dateString, frq);
		p.setDataDate(sqlDate(date));
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

	protected LocalDate getDate(String dateString, String frequencyId) throws MyException {
		String year = dateString.substring(0, 4);
		String month = getMonth(frequencyId, dateString);
		String day = getDay(Integer.valueOf(month), Integer.valueOf(year));
		String adjustedDateString = year + "-" + month + "-" + day;
		return resolveValidDate(adjustedDateString);
	}

	private String getMonth(String frequencyId, String dateString) {
		switch(frequencyId) {
		case FREQUENCY_MONTHLY: return dateString.substring(5, 7);
		case FREQUENCY_QUARTERLY:
			String period = dateString.substring(5, 6);
			return String.valueOf(Integer.valueOf(period) * 3);
		case FREQUENCY_ANNUAL: return "12";
		default: return null;
		}
	}

	private String getDay(int month, int year) {
		String day = "31";
		if (month == 2) {
			day = "28";
			if (year % 4 == 0 && year % 100 != 0) {
				day = "29";
			}
			if (year % 400 == 0 && year % 4000 != 0) {
				day = "29";
			}
		} else if (month == 4 | month == 6 | month == 9 | month == 11) {
			day = "30";
		}
		return day;
	}

	private String getFrequencyId(String dateString) throws MyException {
		switch(dateString.length()) {
		case 4: return FREQUENCY_ANNUAL;
		case 6: return FREQUENCY_QUARTERLY;
		case 7: return FREQUENCY_MONTHLY;
		default: throw new MyException("Cannot resolve frequency from String: " + dateString + ". ");
		}
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
		sharedParams.put(PARAM_RISK_FACTOR, rf);
		super.onError();
	}
}
