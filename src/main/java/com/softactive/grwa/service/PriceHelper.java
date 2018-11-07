package com.softactive.grwa.service;

import java.sql.Date;
import java.util.List;

import org.joda.time.LocalDate;

import com.softactive.core.service.AbstractDataService;
import com.softactive.grwa.object.Price;
import com.softactive.grwa.object.RiskFactor;

public class PriceHelper extends AbstractDataService<Price> {

	@Override
	public String tableName() {
		return "prc_price";
	}
	
	@Override
	public Price findUnique(Price p) {
		return findByRiskFactorIdAndDate(p.getRiskFactorId(), p.getDataDate());
	}
	
	public Price findByRiskFactorIdAndDate(Integer rfId, Date date) {
		String sql = initQuery() + " where risk_factor_id=" + rfId +
				" and data_date='" + date + "' limit 1";
		List<Price> answer = query(sql);
		if (answer.size() == 1) {
			return answer.get(0);
		}
		return null;
	}
	
	private Date sqlDate(LocalDate d) {
		return new Date(d.toDate().getTime());
	}
	
	private List<Price> findByRiskFactors(List<RiskFactor> riskFactors){
		String sql = initQuery() + " where risk_factor_id " + getWhereStatementForRiskFactors(riskFactors);
		return query(sql);
	}
	
	public void deleteByRiskFactor(List<RiskFactor> riskFactors) {
		List<Price> list = findByRiskFactors(riskFactors);
		for(Price rf:list) {
			delete(rf.getId());
		}
	}
	
	private String getWhereStatementForRiskFactors(List<RiskFactor> riskFactors) {
		String sql = " in ('";
		for(RiskFactor rf:riskFactors) {
			sql += rf.getId() + "','";
		}
		sql = sql.substring(0,sql.length()-2);
		sql += ")";
		return sql;
	}

	@Override
	public void update(Price t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int insert(Price t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Price> query(String sql) {
		// TODO Auto-generated method stub
		return null;
	}
}
