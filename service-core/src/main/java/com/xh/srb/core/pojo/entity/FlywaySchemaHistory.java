package com.xh.srb.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FlywaySchemaHistory对象", description="")
public class FlywaySchemaHistory implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "installed_rank", type = IdType.AUTO)
    private Integer installedRank;

    private String version;

    private String description;

    private String type;

    private String script;

    private Integer checksum;

    private String installedBy;

    private LocalDateTime installedOn;

    private Integer executionTime;

    private Boolean success;


}
