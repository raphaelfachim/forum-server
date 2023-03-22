package fachim.raphael.spring.learn.mongo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "questoes")
@Data
public class Question {

    @Id
    private String id;

    private Long userId;

    private String title;

    private String content;
    private Map<String, String> additionalContent;
    private List<Long> votesUp; // user Ids
    private List<Long> votesDown;  // user Ids

    protected Question() {
    }

    public Question(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.additionalContent = new HashMap<>();
        this.votesUp = new ArrayList<>();
        this.votesDown = new ArrayList<>();
    }

    public void addContent(String key, String value){
        getAdditionalContent().put(key, value);
    }
}
