package com.softactive.grwa.object;

public interface GrwaConstants {

	public static final String SOURCE_POLITICAL_RISK = "PR";
	public static final String SOURCE_ECONOMIC_FREEDOM = "EF";
	public static final String SOURCE_WORLD_BANK = "WB";
	public static final String SOURCE_FRED = "FR";
	public static final String SOURCE_REUTERS = "RE";

	public static final String TYPE_COUNTRY = "CNT";
	public static final String TYPE_REGION = "RG";
	public static final String TYPE_GLOBAL = "GL";
	public static final String TYPE_GROUP = "GRP";
	public static final String TYPE_WORLD = "WLD";
	public static final String CODE_WORLD = "1W";
	public static final String CODE_US = "US";

	public static final String COLUMN_POLITICAL_RISK = "pr";
	public static final String COLUMN_ECONOMIC_FREEDOM = "ef";
	public static final String COLUMN_FRED_OTHER = "fo";
	public static final String COLUMN_US = "us";
	public static final String COLUMN_WORLD_BANK = "wr";
	public static final String COLUMN_REUTERS = "re";

	public static final String COLUMN_DAILY = "daily";
	public static final String COLUMN_MONTHLY = "monthly";
	public static final String COLUMN_QUARTERLY = "quarterly";
	public static final String COLUMN_ANNUAL = "annual";

	public static final String COLUMN_SOURCE_CODE = "source_code";

	public static final String FREQUENCY_5_YEAR = "5Y";
	public static final String FREQUENCY_ANNUAL = "A";
	public static final String FREQUENCY_QUARTERLY = "Q";
	public static final String FREQUENCY_MONTHLY = "M";
	public static final String FREQUENCY_WEEKLY = "W";
	public static final String FREQUENCY_DAILY = "D";

	public static final String SAVE_WORLD_BANK_ANNUAL = "WBA";
	public static final String SAVE_WORLD_BANK_QUARTERLY = "WBQ";
	public static final String SAVE_WORLD_BANK_MONTHLY = "WBM";

	public static final String SAVE_WORLD_BANK_RISK_FACTOR = "RFG";
	public static final String SAVE_FRED_US_HISTORICAL = "FUH";
	public static final String SAVE_FRED_US_UPDATE = "FUH";

	public static final String SAVE_FRED_RISK_FACTOR_US = "FRFU";
	public static final String SAVE_FRED_RISK_FACTOR_OTHER = "FRFO";
	public static final String SAVE_ECONOMIC_FREEDOM = "EF";
	public static final String SAVE_POLITICAL_RISK = "PR";
	public static final String SAVE_REUTERS = "RE";
	
	public static final String PARAM_REGION = "region";
	public static final String PARAM_INDICATOR = "indicator";
	public static final String PARAM_RISK_FACTOR = "risk_factor";
	public static final String PARAM_UPDATE_DATE = "update_date";
	public static final String PARAM_INDICATOR_MAP = "indicator_map";



	static final String[] FREQUENCIES = new String[] {
			FREQUENCY_5_YEAR,
			FREQUENCY_ANNUAL,
			FREQUENCY_QUARTERLY,
			FREQUENCY_MONTHLY,
			FREQUENCY_WEEKLY,
			FREQUENCY_DAILY
	};

	static final String[] SOURCES = new String[] {
			SOURCE_WORLD_BANK,
			SOURCE_FRED
	};
}
