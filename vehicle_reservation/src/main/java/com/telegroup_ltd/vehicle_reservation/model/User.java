package com.telegroup_ltd.vehicle_reservation.model;

import com.telegroup_ltd.vehicle_reservation.common.interfaces.Deletable;
import com.telegroup_ltd.vehicle_reservation.common.interfaces.HasCompanyId;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Objects;

@SqlResultSetMapping(
        name = "UserMapping",
        classes = @ConstructorResult(
                targetClass = User.class,
                columns = {
                        @ColumnResult(name="id", type = Integer.class),
                        @ColumnResult(name="username", type = String.class),
                        @ColumnResult(name="first_name", type = String.class),
                        @ColumnResult(name="last_name", type = String.class),
                        @ColumnResult(name="email", type = String.class),
                        @ColumnResult(name="registration_date", type = Date.class),
                        @ColumnResult(name="active", type = Byte.class),
                        @ColumnResult(name="deleted", type = Byte.class),
                        @ColumnResult(name="token", type = String.class),
                        @ColumnResult(name="company_id", type = Integer.class),
                        @ColumnResult(name="role_id", type = Integer.class),
                        @ColumnResult(name="notification_type_id", type = Integer.class)
                }
        )
)

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Deletable, HasCompanyId {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Timestamp registrationDate;
    private Byte active;
    private Byte deleted;
    private String token;
    private Integer companyId;
    private Integer roleId;
    private Integer notificationTypeId;

    public User() {
    }

    public User(Integer id, String username, String firstName,
                String lastName, String email, Date registrationDate,
                Byte active, Byte deleted, String token, Integer companyId,
                Integer roleId, Integer notificationTypeId) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.registrationDate = registrationDate == null ? null : new Timestamp(registrationDate.getTime());
        this.active = active;
        this.deleted = deleted;
        this.token = token;
        this.companyId = companyId;
        this.roleId = roleId;
        this.notificationTypeId = notificationTypeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 128)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 128)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 128)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 128)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "registration_date", nullable = false)
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "deleted", nullable = false)
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "token", nullable = true, length = 64)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "company_id", nullable = true)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "role_id", nullable = false)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "notification_type_id", nullable = true)
    public Integer getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(Integer notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                ", deleted=" + deleted +
                ", token='" + token + '\'' +
                ", companyId=" + companyId +
                ", roleId=" + roleId +
                ", notificationTypeId=" + notificationTypeId +
                '}';
    }

}
