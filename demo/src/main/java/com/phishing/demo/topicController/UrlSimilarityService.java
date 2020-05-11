package com.phishing.demo.topicController;

import com.phishing.demo.urisimilarities.URIsimilarities;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UrlSimilarityService {

    @Resource
    private URIsimilarities urIsimilarities;
    public List<String> validateUrl(UrlRequest request) {
        return urIsimilarities.getDetail(request.getUrl());
    }
}
