package com.mo.schedule;

import com.mo.entity.Article;
import com.mo.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ArticleViewSync {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ArticleService articleService;

    /**
     * 每半个小时将文章阅读量写入SQL
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void syncViewCount() {
        log.info("阅读量开始写入SQL");

        Map<Object, Object> viewMap = redisTemplate.opsForHash().entries("blog:article_view");

        if (viewMap.isEmpty()) {
            return;
        }

        List<Article> articleList = new ArrayList<>();
        viewMap.forEach((key, value) -> {
            Integer articleId = Integer.valueOf(key.toString());
            Integer viewCount = Integer.valueOf(value.toString());

            Article article = new Article();
            article.setId(articleId);
            article.setViewCount(viewCount);
            articleList.add(article);
        });

        articleService.updateBatchById(articleList);
        log.info("同步 {} 条文章阅读量", articleList.size());
    }

    /**
     * 每天的0点清空今天阅读量
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteTodayViewCount() {
        log.info("刷新今日阅读量");
        redisTemplate.delete("blog:today_view");
    }

}
