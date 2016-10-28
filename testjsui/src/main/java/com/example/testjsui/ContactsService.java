package com.example.testjsui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class ContactsService {
    /**
     * 模拟获取信息
     * @return
     */
    public List<Contacts> getContactsImf(){
        List<Contacts> contacts=new ArrayList<Contacts>();
        contacts.add(new Contacts(2009,"CrazyMO",9389281));
        contacts.add(new Contacts(2010,"Jim中国",5641021));
        contacts.add(new Contacts(2011,"Winds",897512));
        contacts.add(new Contacts(2012,"Jack",4524323));
        return contacts;
    }
}
