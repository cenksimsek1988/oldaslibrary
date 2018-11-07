package com.softactive.grwa.fred;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.softactive.core.exception.MyError;
import com.softactive.core.exception.MyException;
import com.softactive.core.manager.AbstractJsonObjectHandler;
import com.softactive.grwa.object.GrwaConstants;
import com.softactive.grwa.object.Indicator;

public abstract class AbstractFredHandler<OUTPUT_COMPONENT> extends AbstractJsonObjectHandler<OUTPUT_COMPONENT>  implements GrwaConstants{

	public AbstractFredHandler(Map<String, Object> sharedParams) {
		super(sharedParams);
	}

	private static final long serialVersionUID = 7060157568515342932L;
	public static final String RELEASES = "releases";
	public static final String ID = "id";
	public static final String NAME = "id";
	public static final String COUNT = "count";
	public static final String OFFSET = "offset";
	public static final String LIMIT = "limit";
	public static final String SERIES = "seriess";
	public static final String OBSERVATIONS = "observations";

	
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
	protected void mapMetaData(JSONObject r) throws MyException {
		try {
			Integer offset = r.getInt(OFFSET);
			sharedParams.put(OFFSET, offset);
			Integer limit = r.getInt(LIMIT);
			sharedParams.put(LIMIT, limit);
			Integer count = r.getInt(COUNT);
			sharedParams.put(COUNT, count);
		} catch (JSONException e) {
			throw new MyException("Error while parsing meta data: " + e.getMessage());
		}
	}

	@Override
	protected boolean hasNext(Map<String, Object> metaMap) {
		Integer offset = (Integer) metaMap.getOrDefault(OFFSET, null);
		Integer limit = (Integer) metaMap.getOrDefault(LIMIT, null);
		Integer count = (Integer) metaMap.getOrDefault(COUNT, null);
		return (offset + limit) < count;
	}
}
