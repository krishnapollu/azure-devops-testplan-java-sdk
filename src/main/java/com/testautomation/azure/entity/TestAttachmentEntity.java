package com.testautomation.azure.entity;

import lombok.Data;

@Data
public class TestAttachmentEntity {

    String attachmentType = "";
    String comment = "";
    String fileName;
    String stream;
}
