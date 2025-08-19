package net.etfbl.ip.urban_motion.controller;


import net.etfbl.ip.urban_motion.service.RssFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rss")
public class RssFeedController {
    private final RssFeedService rssFeedService;

    @Autowired
    public RssFeedController(RssFeedService rssFeedService) {
        this.rssFeedService = rssFeedService;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String getRssFeed() {
        return rssFeedService.generateRssFeed();
    }
}