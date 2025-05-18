package com.cruru.email.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailQueueMessage implements Serializable {

    private Long clubId;
    private Long applicantId;
    private String toEmail;
    private String subject;
    private String content;
    private List<String> attachmentPaths;
}
