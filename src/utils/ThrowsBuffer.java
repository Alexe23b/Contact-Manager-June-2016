package utils;

import parse.Contact;

/**
 * Created by alexe on 08.07.2016.
 */
public class ThrowsBuffer {
    private Contact contact = null;

    public synchronized void put(Contact newContact) throws InterruptedException{
        while (contact != null){
            this.wait();
        }
        this.contact = newContact;
        this.notifyAll();
    }

}
