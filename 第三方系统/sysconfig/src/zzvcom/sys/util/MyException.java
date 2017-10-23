
package zzvcom.sys.util;

public class MyException extends Exception {

	public MyException()
	{
		super();
	}
	public MyException(String info)
	{
		super(info);
	}
	public MyException(Object obj,String method,String msg) throws Exception {
        throw new MyException("[class:"+obj.getClass().getName()+"][method:"+method+"]["+msg+"]");
	}
    
    public MyException(Object obj,String method,Exception e)  throws Exception{
        throw new MyException("[class:"+obj.getClass().getName()+"][method:"+method+"]["+e.getMessage()+"]");
    }
	public MyException(Throwable cause) {
		super(cause);
	}

    public int test() throws MyException
    {
        try{
        int k = 1/0;
        return k;
        }catch(Exception e)
        {
            throw new MyException("0");
        }
     
        
        }
	public static void main(String[] args) throws MyException {
        MyException my = new MyException();
	    my.test();
        //System.out.println("fdsafdas");
	}
}
