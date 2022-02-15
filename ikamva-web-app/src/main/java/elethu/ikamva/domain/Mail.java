package elethu.ikamva.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class Mail {
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String contentType;
    private String mailContent;
    private List<Object> attachments;

    public Mail(){
        this.contentType = "text/plain";
    }
}
