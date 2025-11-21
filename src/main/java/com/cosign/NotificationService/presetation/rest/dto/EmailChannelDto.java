package com.cosign.NotificationService.presetation.rest.dto;

import lombok.Data;

@Data
public class EmailChannelDto {
    private String subject;
    private String bodyHtml;
    private String bodyText;
}
