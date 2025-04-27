package com.Banking.backend.dto.request;

import com.Banking.backend.Enums.ApplicationStatus;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusChangeApplicationDTO {

    private Long id;
    private ApplicationStatus status;

}
