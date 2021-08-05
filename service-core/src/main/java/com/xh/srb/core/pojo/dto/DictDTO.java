package com.xh.srb.core.pojo.dto;

import com.xh.srb.core.pojo.entity.Dict;
import lombok.Data;

@Data
public class DictDTO extends Dict {
    private Boolean hasChildren;
}
