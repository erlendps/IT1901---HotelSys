```plantuml
@startuml

    actor user

    user -> ui: click "Make reservation"
    ui -> HotelAccess: makeReservation()
    HotelAccess -> core: makeReservation()
    core -> core: validate
    HotelAccess <-- core: Success
    HotelAccess -> persistence: save to file

    core <-- persistence: saved
    HotelAccess <-- core: response
    ui <-- HotelAccess: added
    user <-- ui: Reservation added

@enduml
```
