package com.demo.rbac.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author 戴俊明
 * @version 1.0
 * @className RoleAccess
 * @description 角色与权限的实体类
 * @date 2019/5/20 18:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role_access")
public class RoleAccess implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Min(value = 1, message = "id最小不能小于1")
    private Integer id;

    private Integer rid;

    private Integer aid;
}