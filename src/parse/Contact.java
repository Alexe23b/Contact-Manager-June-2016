package parse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.LocalGregorianCalendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexe on 10.06.2016.
 */


public class Contact {
    @SerializedName("uids")
    @Expose
    private String uids;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("phones")
    @Expose
    private List<String> phones = new ArrayList<>();

    @SerializedName("emails")
    @Expose
    private List<String> emails = new ArrayList<>();

    @SerializedName("photoPath")
    @Expose
    private String photoPath;

    @SerializedName("birthday")
    @Expose
    private LocalDate birthday;

    public String getUids() {
        return uids;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}