package com.zzvcom.sysmag.persistence;

import org.apache.commons.dbcp.BasicDataSource;

import com.zzvcom.sysmag.util.Vcom_3DES;

/**
 * @author Wang Xiaoming
 */
public class DataSource extends BasicDataSource {

	/**
	 * 重写数据源设置密码，植入密码解密
	 */
	@Override
	public synchronized void setPassword(String password) {
		Vcom_3DES vcom3DES = new Vcom_3DES();
		vcom3DES.setIsEncrypt(0);
		vcom3DES.setMessage(password);
		super.setPassword(vcom3DES.Vcom3DESChiper());
	}

}
