package zzvcom.sys.util;

import org.logicalcobwebs.proxool.Vcom_3DES;


public class Create3DSPassWord {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*字符串加密过程*/
		Vcom_3DES tempDesEn = new Vcom_3DES(1,"mcenter","123456789012345678901234");
		String strTemp = tempDesEn.Vcom3DESChiper();		
		System.out.println(strTemp);
		
		/*字符串解密过程*/		
		Vcom_3DES tempDe= new Vcom_3DES(0,"3c1a2b7c1855aab6","123456789012345678901234");
		String strTempDe = tempDe.Vcom3DESChiper();		
		System.out.println(strTempDe);
		
		String[] sessionid_s = "admin#111111#1312527689718".split("#");
		System.out.println("aaaaaaaa="+sessionid_s.length);
		System.out.println("bbbbbbbb="+(System.currentTimeMillis() - Long.parseLong(sessionid_s[2])));

	}

}
