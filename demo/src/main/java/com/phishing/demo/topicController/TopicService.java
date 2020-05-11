package com.phishing.demo.topicController;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

    private List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("spring","spring framework","hdjdk  "),
            new Topic("ashutosh","deowanshi","sac state  ")

        ));
    public List<Topic> getAllTopics(){
        return topics;


        }
    public Topic getTopic(String id) {
        return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }
    public void addTopic(Topic topic){
        topics.add(topic);
    }
}
