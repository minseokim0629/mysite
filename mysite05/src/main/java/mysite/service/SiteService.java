package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;

@Service
public class SiteService {
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}

	public SiteVo getSite() {
		SiteVo siteVo = siteRepository.findById();
		return siteVo;
	}

	public void updateSite(SiteVo siteVo) {
		siteRepository.update(siteVo);
	}
}