package com.microdev.param;

import lombok.Data;

@Data
public class WorkerQueryDTO {
    private String hrId;
    private String name;
    private String pollCode;
    private String hotelId;
    private Integer status;
}
