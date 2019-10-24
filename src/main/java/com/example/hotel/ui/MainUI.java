package com.example.hotel.ui;

import com.example.hotel.model.Booking;
import com.example.hotel.model.Client;
import com.example.hotel.model.Room;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.ClientRepository;
import com.example.hotel.repository.RoomRepository;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Comparator;

@SpringUI(path = "")
public class MainUI extends UI {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    RoomRepository roomRepository;

    private VerticalLayout root;
    private TextField nameTextField;
    private TextField personNumberTextField;
    private DateField fromField;
    private DateField toField;
    private CheckBox viewOnMountainsCheckBox;
    private Button bookRoom;
    private  HorizontalLayout horizontalLayout;
    private Grid<Booking> bookingGrid;

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.setSizeUndefined();
        horizontalLayout = new HorizontalLayout();

        nameTextField = new TextField();
        nameTextField.setCaption("Nazwa klienta: ");
        nameTextField.setPlaceholder("Wpisz nazwę klienta: ");

        personNumberTextField = new TextField();
        personNumberTextField.setPlaceholder("Wpisz liczbę osób: ");
        personNumberTextField.setCaption("Liczba osób: ");

        fromField = new DateField();
        toField = new DateField();

        fromField.setCaption("Data początku: ");
        fromField.setValue(LocalDate.now());
        fromField.setTextFieldEnabled(false);

        toField.setCaption("Data końca: ");
        toField.setValue(LocalDate.now().plusDays(5));
        toField.setTextFieldEnabled(false);

        viewOnMountainsCheckBox = new CheckBox("Pokój z widokiem na góry");
        viewOnMountainsCheckBox.setValue(false);

        bookRoom = new Button("Zarezerwuj pokój");
        bookRoom.addClickListener(event -> {
            try {
                String nameClient = nameTextField.getValue();
                Integer numberPerson = Integer.valueOf(personNumberTextField.getValue());
                LocalDate fromLocalDate = fromField.getValue();
                LocalDate toLocalDate = toField.getValue();
                Boolean isViewOnMountains = viewOnMountainsCheckBox.getValue();
                if (nameClient != null && !nameClient.trim().equals("")) {
                    if (fromLocalDate.isBefore(toLocalDate)) {

                        Room room = roomRepository.findAll().stream()
                                .filter(r -> bookingRepository.findByRoom(r).stream()
                                        .allMatch(booking -> fromLocalDate.isAfter(booking.getToDate())
                                                || (toLocalDate.isBefore(booking.getFromDate()))))
                                .filter(r -> r.getViewOnMountains() == isViewOnMountains)
                                .filter(r -> r.getPersonsNumber() >= numberPerson)
                                .min(Comparator.comparing(Room::getPricePerDay).thenComparing(Room::getPersonsNumber))
                                .orElse(null);

                        if (room != null) {
                            Client client = new Client(0L, nameClient);
                            client = clientRepository.save(client);
                            bookingRepository.save(new Booking(0L, client, room, fromLocalDate, toLocalDate));
                            bookingGrid.setItems(bookingRepository.findAll());
                            Notification.show("Pokój został zarezerwowany!", "", Notification.Type.HUMANIZED_MESSAGE);
                        } else {
                            Notification.show("Nie ma wolnych pokoi w tym terminie!", Notification.Type.ERROR_MESSAGE);
                        }
                    } else {
                        Notification.show("Wprowadź poprawne daty!", Notification.Type.ERROR_MESSAGE);
                    }
                } else {
                    Notification.show("Wprowadź imię i nazwisko!", Notification.Type.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                Notification.show("Wprowadź poprawną liczbę osób w pokoju!", Notification.Type.ERROR_MESSAGE);
            }
        });

        bookingGrid = new Grid<>();
        bookingGrid.setWidth("700px");
        bookingGrid.addColumn(Booking::getId).setWidth(50);
        bookingGrid.addColumn(booking -> booking.getClient().getName()).setCaption("Klient");
        bookingGrid.addColumn(booking -> booking.getRoom().getId()).setCaption("Nr pokoju").setWidth(100);
        bookingGrid.addColumn(Booking::getFromDate).setCaption("Od");
        bookingGrid.addColumn(Booking::getToDate).setCaption("Do");
        bookingGrid.setItems(bookingRepository.findAll());

        horizontalLayout.setMargin(true);
        root.addComponents(nameTextField, personNumberTextField, fromField, toField, viewOnMountainsCheckBox, bookRoom, horizontalLayout, bookingGrid);
        setContent(root);
    }
}
