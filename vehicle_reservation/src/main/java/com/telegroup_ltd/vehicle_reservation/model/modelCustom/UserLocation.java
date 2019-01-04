package com.telegroup_ltd.vehicle_reservation.model.modelCustom;

import com.telegroup_ltd.vehicle_reservation.model.User;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.sql.Date;
import java.sql.Timestamp;

@SuppressWarnings("WeakerAccess")
@SqlResultSetMapping(
        name = "UserLocationMapping",
        classes = @ConstructorResult(
                targetClass = UserLocation.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "password", type = String.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "email", type = String.class),
                        @ColumnResult(name = "registration_date", type = Date.class),
                        @ColumnResult(name = "active", type = Byte.class),
                        @ColumnResult(name = "deleted", type = Byte.class),
                        @ColumnResult(name = "token", type = String.class),
                        @ColumnResult(name = "company_id", type = Integer.class),
                        @ColumnResult(name = "role_id", type = Integer.class),
                        @ColumnResult(name = "notification_type_id", type = Integer.class),
                        @ColumnResult(name = "location_name", type = String.class)
                }
        )
)
@MappedSuperclass
public class UserLocation extends User {

    private String locationName;

    public UserLocation() {
    }

    @SuppressWarnings("WeakerAccess")
    public UserLocation(Integer id, String username, String password, String firstName,
                        String lastName, String email, Timestamp registrationDate,
                        Byte active, Byte deleted, String token, Integer companyId,
                        Integer roleId, Integer notificationTypeId, String locationName) {
        super(id, username, password, firstName, lastName, email, registrationDate,
                active, deleted, token, companyId, roleId, notificationTypeId);
        this.locationName = locationName;
    }

    @SuppressWarnings("WeakerAccess")
    public UserLocation(User user, String locationName) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getEmail(), null, user.getActive(),
                user.getDeleted(), user.getToken(), user.getCompanyId(), user.getRoleId(),
                user.getNotificationTypeId());
        setRegistrationDate(user.getRegistrationDate() == null ? null : new Timestamp(
                user.getRegistrationDate().getTime()));
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
