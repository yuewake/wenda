package com.yue.wenda.service;

import com.yue.wenda.dao.FeedDao;
import com.yue.wenda.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yue on 2019/3/4
 */
@Service
public class FeedService {
    @Autowired
    FeedDao feedDao;

    /**
     * 获取用户[userIds]们产生的feed
     * @param maxId
     * @param userIds
     * @param count
     * @return
     */
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDao.selectUserFeeds(maxId, userIds, count);
    }

    public boolean addFeed(Feed feed) {
        feedDao.addFeed(feed);
        return feed.getId() > 0;
    }

    public Feed getById(int id) {
        return feedDao.getFeedById(id);
    }
}
