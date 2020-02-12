package com.fogfore.qrcodeauth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("message")
public class Message {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("from_uid")
    private Integer fromUid;
    @TableField("to_uid")
    private Integer toUid;
    @TableField("type")
    private String type;
    @TableField("address_id")
    private Integer addressId;
}
