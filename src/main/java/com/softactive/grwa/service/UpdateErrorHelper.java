package com.softactive.grwa.service;

import java.util.List;

import com.softactive.core.service.AbstractDataService;
import com.softactive.grwa.object.UpdateError;

public class UpdateErrorHelper extends AbstractDataService<UpdateError> {

	@Override
	public String tableName() {
		return "update_error";
	}

	@Override
	public UpdateError findUnique(UpdateError ue) {
		return findByRiskFactorId(ue.getRiskFactorId());
	}


	public UpdateError findByRiskFactorId(Integer rfId) {
		String sql = initQuery() + " where risk_factor_id=" + rfId + " LIMIT 1";
		List<UpdateError> list = query(sql);
		if(list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(UpdateError t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int insert(UpdateError t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UpdateError> query(String sql) {
		// TODO Auto-generated method stub
		return null;
	}
}
