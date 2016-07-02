package parse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexe on 24.06.2016.
 */
public class Address {
    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("houseNumber")
    @Expose
    private String houseNumber;

    @SerializedName("houseSuffix")
    @Expose
    private String houseSuffix;

    @SerializedName("apartment")
    @Expose
    private String apartment;

    @SerializedName("postCode")
    @Expose
    private String postCode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseSuffix() {
        return houseSuffix;
    }

    public void setHouseSuffix(String houseSuffix) {
        this.houseSuffix = houseSuffix;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}