package com.telegroup_ltd.vehicle_reservation.model.modelCustom;

import com.telegroup_ltd.vehicle_reservation.model.Logger;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.io.Serializable;
import java.util.Date;

@SqlResultSetMapping(
        name = "LoggerCompanyUserRoleMapping",
        classes = @ConstructorResult(
                targetClass = LoggerCompanyUserRole.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "action_type", type = String.class),
                        @ColumnResult(name = "action_details", type = String.class),
                        @ColumnResult(name = "table_name", type = String.class),
                        @ColumnResult(name = "created", type = Date.class),
                        @ColumnResult(name = "atomic", type = Byte.class),
                        @ColumnResult(name = "user_id", type = Integer.class),
                        @ColumnResult(name = "company_id", type = Integer.class),
                        @ColumnResult(name = "company_name", type = String.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "role_name", type = String.class)
                }
        )
)

@MappedSuperclass
public class LoggerCompanyUserRole extends Logger implements Serializable {

    private String companyName;
    private String username;
    private String roleName;

    public LoggerCompanyUserRole() {
    }

    public LoggerCompanyUserRole(Integer id, String actionType, String actionDetails,
                                 String tableName, Date created, Byte atomic, Integer userId,
                                 Integer companyId, String companyName, String username, String roleName) {
        super(id, actionType, actionDetails, tableName, created, atomic, userId, companyId);
        this.companyName = companyName;
        this.username = username;
        this.roleName = roleName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
