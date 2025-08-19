package net.etfbl.ip.urban_motion.service;

import com.rometools.rome.feed.rss.*;
import com.rometools.rome.io.WireFeedOutput;

import net.etfbl.ip.urban_motion.repository.PostRepository;
import net.etfbl.ip.urban_motion.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssFeedService {
    private final PostRepository postRepository;
    private final PromotionRepository promotionRepository;

    @Autowired
    public RssFeedService(PostRepository postRepository, PromotionRepository promotionRepository) {
        this.postRepository = postRepository;
        this.promotionRepository = promotionRepository;
    }

    public String generateRssFeed() {
        Channel channel = new Channel("rss_2.0");
        channel.setTitle("Promotions and Posts");
        channel.setLanguage("en-us");
        channel.setDescription("This feed provides information about the latest promotions and posts.");
        channel.setLink("http://localhost:8080/rss");

        List<Item> feedItems = new ArrayList<>();

        postRepository.findAll().forEach(post -> {
            Item item = new Item();
            item.setTitle(post.getTitle());
            Description description = new Description();
            description.setValue(post.getContent());
            item.setDescription(description);
            item.setPubDate(Timestamp.valueOf(post.getCreatedAt()));
            feedItems.add(item);
        });

        promotionRepository.findAll().forEach(promotion -> {
            Item item = new Item();
            item.setTitle("Promotion: " + promotion.getTitle());
            Description description = new Description();
            description.setValue(promotion.getDescription() + " (Valid until: " + promotion.getEndDate() + ")");
            item.setDescription(description);
            item.setPubDate(Timestamp.valueOf(promotion.getCreationDate()));
            feedItems.add(item);
        });

        channel.setItems(feedItems);


        try {
            return new WireFeedOutput().outputString(channel);
        } catch (Exception e) {
            throw new RuntimeException("Error generating RSS feed", e);
        }
    }
}
