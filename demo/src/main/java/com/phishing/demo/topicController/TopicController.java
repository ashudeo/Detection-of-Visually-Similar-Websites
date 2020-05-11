package com.phishing.demo.topicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Resource
    private UrlSimilarityService urlSimilarityService;

    @RequestMapping("/topics")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();

    }
    @RequestMapping("/topics/{id}")
    public Topic getTopic(@PathVariable String id) {
        return topicService.getTopic(id);
    }

    @RequestMapping(method= RequestMethod.POST, value = "/topics")
    public List<String> addTopic(@RequestBody UrlRequest request){
        System.out.println(request.getUrl());
        return urlSimilarityService.validateUrl(request);
    }
}
