package com.example.ranacom.phonebook;


public class ContactList {

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    private int phoneId;
    private String IVContactImage;
    private String contactName;
    private String phoneNumber;
    private String emailAddress;



    private String contactId;

    public ContactList(String name, String number, String image_uri) {
        setContactName(name);
        setPhoneNumber(number);
        setIVContactImage(image_uri);

    }

    public ContactList(String name, String number, String image_uri, String email,String contactId) {
        setContactName(name);
        setPhoneNumber(number);
        setIVContactImage(image_uri);
        setEmailAddress(email);
        setContactId(contactId);
    }

    public ContactList(String name, String number) {
        setContactName(name);
        setPhoneNumber(number);
    }

    public ContactList() {
    }
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getIVContactImage() {
        return IVContactImage;
    }

    public void setIVContactImage(String IVContactImage) {
        this.IVContactImage = IVContactImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


}
