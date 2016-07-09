package utils;

import parse.Contact;

/**
 * Created by alexe on 08.07.2016.
 */
public class ThrowsBuffer {
    private Contact contact = null;

    public synchronized void put(Contact newContact) throws InterruptedException {
        while (contact != null) {
            this.wait();
        }
        this.contact = newContact;
        System.out.println("Contact " + contact.getFirstName() + "was read");
        this.notifyAll();
    }

    public synchronized Contact get() throws InterruptedException {
        while (contact == null) {
            this.wait();
        }
        Contact parcelContact = this.contact;
        System.out.println("Contact " + parcelContact.getFirstName() + "was wrote");
        this.contact = null;
        this.notifyAll();
        return parcelContact;
    }
}
