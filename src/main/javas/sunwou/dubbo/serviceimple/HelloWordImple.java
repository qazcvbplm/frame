package sunwou.dubbo.serviceimple;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import sunwou.dubbo.service.HelloWord;

@Component("HelloWordImple")
@Service
public class HelloWordImple implements HelloWord{

	@Override
	public String sayHello() {
		// TODO Auto-generated method stub
		return "hello dubbo";
	}

}
