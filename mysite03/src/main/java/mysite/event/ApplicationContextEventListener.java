package mysite.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import mysite.service.SiteService;

public class ApplicationContextEventListener {
	@Autowired
	private ApplicationContext applicationContext;

	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshEvent() {
		SiteService siteService = applicationContext.getBean(SiteService.class);
		System.out.println("-- Context Refreshed Event Received --" + siteService);
		// 구현한 siteVo를? bean으로 등록하는 작업 및 jsp로 넘겨주기
		//viewResolver에게 container에 잇는 걸 어떻게 노출시키냐?
	}
}
