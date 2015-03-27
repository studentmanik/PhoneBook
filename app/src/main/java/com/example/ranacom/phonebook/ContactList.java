package com.example.ranacom.phonebook;


public class ContactList {

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    private int phoneId;
    private int IVContactImage;
    private String contactName;
    private String phoneNumber;
    public ContactList(String name,String number){
        setContactName(name);
        setPhoneNumber(number);

    }
    public ContactList(){}

    public int getIVContactImage() {
        return IVContactImage;
    }

    public void setIVContactImage(int IVContactImage) {
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
