package com.softactive.grwa.object;

import com.softactive.core.object.Base;
import com.softactive.core.utils.JdbcColumn;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Frequency extends Base{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3715468812369289142L;
	@JdbcColumn(field = "name", keyValue = JdbcColumn.VALUE)
	private String name;

	@JdbcColumn(field = "id", keyValue = JdbcColumn.KEY)
	protected Integer id;

}
