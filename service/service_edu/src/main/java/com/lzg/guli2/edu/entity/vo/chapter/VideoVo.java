package com.lzg.guli2.edu.entity.vo.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("课时信息")
public class VideoVo implements Serializable {
    private static final long serialVersionUID = 1L;
//    @ApiModelProperty(value = "视频id")
    private String id;
//    @ApiModelProperty(value = "节点名称")
    private String title;
//    @ApiModelProperty(value = "是否可以试听")
    private Boolean free;   //这里需要进行转换

    private String videoSourceId;  //视频的id


}
