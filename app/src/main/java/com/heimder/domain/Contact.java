package com.heimder.domain;

/**
 * Created by JoaoEmilio on 25/12/2015.
 */
public class Contact extends Domain {

    private Long contactid;
    private String name;
    private String mobile;
    private String email;
    private String facebook;

    public Long getContactid() {
        return contactid;
    }

    public void setContactid(Long contactid) {
        this.contactid = contactid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
