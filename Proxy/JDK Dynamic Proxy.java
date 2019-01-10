package proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

public class JDKProxy{
	//写法1
	private Object targetObject;//代理目标
	public Object CustomerProxy(Object obj) {
		targetObject = obj;
		return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(),new targetHandler());
	}
	class targetHandler  implements InvocationHandler{
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			 System.out.println("开启事务...");  
             Object returnValue = method.invoke(targetObject, args);//回调被代理目标的方法userDaoImpl.add();  
             System.out.println("提交事务");  
             return returnValue;  
		}
	}
	public static void main(String[] args) {  
		JDKProxy jdkProxy = new JDKProxy();  
        Customer userDao = (Customer)jdkProxy.CustomerProxy(new CustomerImpl());  
        userDao.shopping();  
    }  
	//写法2
	@Test
	public void test1(){
		Customer customer = new CustomerImpl();
		Customer cus = (Customer) Proxy.newProxyInstance(customer.getClass().getClassLoader(), customer.getClass().getInterfaces(),new InvocationHandler(){
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// TODO Auto-generated method stub
				return method.invoke(proxy, args);
			}
			
		});
		cus.shopping();
	}
}
