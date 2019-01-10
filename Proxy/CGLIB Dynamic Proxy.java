package proxy.cglib;

import java.lang.reflect.Method;

import org.junit.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import proxy.model.Customer;

public class CGLIBProxy {
	//写法1
	private Object targetObject;
	private Object createProxy(Object obj){
		targetObject = obj;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetObject.getClass());//设置代理对象，父类，说明是继承，所有代理对象不能为final
		enhancer.setCallback(new MyHandler());
		return enhancer.create();//创建代理
	}
	class MyHandler implements MethodInterceptor{
		@Override
		public Object intercept(Object arg0, Method method, Object[] args,
				MethodProxy arg3) throws Throwable {
			System.out.println("开启事务..");  
            Object returnValue = method.invoke(targetObject, args);  
            System.out.println("提交事务....");  
            return returnValue;  
		}
	}
	@Test
	public  void test1() {  
		CGLIBProxy cglibProxy = new CGLIBProxy();  
		Customer customer = (Customer)cglibProxy.createProxy(new Customer());  
		customer.eat();  
    }  
	//写法2
	@Test
	public void test2(){
		Customer customer = new Customer();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(customer.getClass());
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object proxy, Method method, Object[] args,
					MethodProxy arg3) throws Throwable {
	            if(method.getName().equals("eat")){  
                    System.out.println("customer的eat方法被拦截了。。。。");  
                    Object invoke = method.invoke(proxy, args);  
                    System.out.println("真实方法拦截之后。。。。");  
                    return invoke;  
                }  
                // 不拦截  
                return method.invoke(proxy, args);  
			}
		});
		Customer cus = (Customer) enhancer.create();
		cus.eat();
	}
}
