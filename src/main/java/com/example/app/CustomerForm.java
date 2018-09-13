package com.example.app;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Alex on 6/17/2017.
 */
public class CustomerForm extends FormLayout {
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField email = new TextField("email");
    private NativeSelect<CustomerStatus> status = new NativeSelect<>("Status");
    private DateField birthdate = new DateField("Birthdate");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");

    private Binder<Customer> binder = new Binder<>(Customer.class);
    private CustomerService service= CustomerService.getInstance();
    private Customer customer;
    private MyUI myUI;

    public CustomerForm(MyUI myUI){
        this.myUI = myUI;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete, cancel);
        addComponents(firstName, lastName, email, status, birthdate, buttons);
        status.setItems(CustomerStatus.values());

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        cancel.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        cancel.addClickListener(e -> this.cancel());
        binder.bindInstanceFields(this);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.setBean(customer);

        delete.setVisible(customer.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    private void delete(){
        service.delete(customer);
        myUI.updateList();
        setVisible(false);
    }

    private void save(){
        service.save(customer);
        myUI.updateList();
        setVisible(false);
    }

    private void cancel(){
        myUI.updateList();
        setVisible(false);
    }
}
