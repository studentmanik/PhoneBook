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
    public ContactList(String name,String number,String image_uri){
        setContactName(name);
        setPhoneNumber(number);
        setIVContactImage(image_uri);

    }
    public ContactList(){}

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
