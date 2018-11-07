package com.softactive.grwa.wb;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.softactive.core.exception.MyError;
import com.softactive.core.exception.MyException;
import com.softactive.core.manager.AbstractJsonArrayHandler;
import com.softactive.grwa.object.GrwaConstants;

public abstract class AbstractWorldBankHandler<OUTPUT_COMPONENT> extends AbstractJsonArrayHandler<OUTPUT_COMPONENT> implements GrwaConstants {
	
	public AbstractWorldBankHandler(Map<String, Object> sharedParams) {
		super(sharedParams);
	}

	private static final long serialVersionUID = -3398406810984198050L;
	public static final Date DUMMY_DATE = Date.valueOf("0001-01-01");
	public static final String LAST_UPDATED = "lastupdated";
	public static final String PAGES = "pages";
	public static final String PAGE = "page";
	public static final String PER_PAGE = "per_page";
	public static final String TOTAL = "total";
	
	@Override
	protected boolean isOutputInvalid(List<OUTPUT_COMPONENT> output) {
		if(output.isEmpty()) {
			MyError er = new MyError(ERROR_EMPTY_OBJECT_LIST, "Output List is empty");
			sharedParams.put(PARAM_ERROR, er);
			return true;
		}
		return false;
	}

	@Override
	protected void mapMetaData(JSONArray r) throws MyException{
		JSONObject o;
		try {
			o = r.getJSONObject(0);
			sharedParams.put(PAGE, o.getInt(PAGE));
			sharedParams.put(PAGES, o.getInt(PAGES));
			sharedParams.put(LAST_UPDATED, o.opt(LAST_UPDATED));
			sharedParams.put(PER_PAGE, o.getInt(PER_PAGE));
			sharedParams.put(TOTAL, o.getInt(TOTAL));
		} catch (JSONException e) {
			throw new MyException("Error while parsing meta data: " + e.getMessage());
		}
	}

	@Override
	protected boolean hasNext(Map<String, Object> metaMap) {
		return (int) metaMap.get(PAGE) < (int) metaMap.get(PAGES);
	}

	@Override
	protected JSONArray inputBody(JSONArray r) throws MyException{
		try {
			return r.getJSONArray(1);
		} catch (JSONException e) {
			throw new MyException("Error while parsing array: " + e.getMessage());
		}
	}
}
