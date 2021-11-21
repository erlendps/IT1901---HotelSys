
[INFO] --- spotbugs-maven-plugin:4.4.1:check (spotbugs-check) @ ui ---
[INFO] BugInstance size is 3
[INFO] Error size is 0
[INFO] Total bugs: 3
[ERROR] Medium: gr2116.ui.main.MainPageController.setHotelAccess(HotelAccess) may expose internal representation by storing an externally mutable object into MainPageController.hotelAccess [gr2116.ui.main.MainPageController] At MainPageController.java:[line 71] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.main.MainPageController.setPerson(Person) may expose internal representation by storing an externally mutable object into MainPageController.person [gr2116.ui.main.MainPageController] At MainPageController.java:[line 88] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.main.UserPanelController.setPerson(Person) may expose internal representation by storing an externally mutable object into UserPanelController.person [gr2116.ui.main.UserPanelController] At UserPanelController.java:[line 57] EI_EXPOSE_REP2
[INFO] 

[INFO] --- spotbugs-maven-plugin:4.4.1:check (spotbugs-check) @ RESTservice ---
[INFO] BugInstance size is 14
[INFO] Error size is 0
[INFO] Total bugs: 14
[ERROR] Medium: gr2116.RESTservice.restapi.HotelService.getHotel() may expose internal representation by returning HotelService.hotel [gr2116.RESTservice.restapi.HotelService] At HotelService.java:[line 40] EI_EXPOSE_REP
[ERROR] Medium: gr2116.RESTservice.restapi.PersonResource.getPerson() may expose internal representation by returning PersonResource.person [gr2116.RESTservice.restapi.PersonResource] At PersonResource.java:[line 76] EI_EXPOSE_REP
[ERROR] Medium: new gr2116.RESTservice.restapi.PersonResource(String, Person, Hotel) may expose internal representation by storing an externally mutable object into PersonResource.hotel [gr2116.RESTservice.restapi.PersonResource] At PersonResource.java:[line 51] EI_EXPOSE_REP2
[ERROR] Medium: new gr2116.RESTservice.restapi.PersonResource(String, Person, Hotel) may expose internal representation by storing an externally mutable object into PersonResource.person [gr2116.RESTservice.restapi.PersonResource] At PersonResource.java:[line 49] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.RESTservice.restapi.PersonResource.setHotelPersistence(HotelPersistence) may expose internal representation by storing an externally mutable object into PersonResource.hotelPersistence [gr2116.RESTservice.restapi.PersonResource] At PersonResource.java:[line 38] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.RESTservice.restapi.RoomResource.getHotelRoom() may expose internal representation by returning RoomResource.room [gr2116.RESTservice.restapi.RoomResource] At RoomResource.java:[line 74] EI_EXPOSE_REP
[ERROR] Medium: new gr2116.RESTservice.restapi.RoomResource(HotelRoom, Hotel) may expose internal representation by storing an externally mutable object into RoomResource.hotel [gr2116.RESTservice.restapi.RoomResource] At RoomResource.java:[line 47] EI_EXPOSE_REP2
[ERROR] Medium: new gr2116.RESTservice.restapi.RoomResource(HotelRoom, Hotel) may expose internal representation by storing an externally mutable object into RoomResource.room [gr2116.RESTservice.restapi.RoomResource] At RoomResource.java:[line 46] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.RESTservice.restapi.RoomResource.setHotelPersistence(HotelPersistence) may expose internal representation by storing an externally mutable object into RoomResource.hotelPersistence [gr2116.RESTservice.restapi.RoomResource] At RoomResource.java:[line 36] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.RESTservice.restserver.HotelConfig.getHotel() may expose internal representation by returning HotelConfig.hotel [gr2116.RESTservice.restserver.HotelConfig] At HotelConfig.java:[line 63] EI_EXPOSE_REP
[ERROR] Medium: gr2116.RESTservice.restserver.HotelConfig.setHotel(Hotel) may expose internal representation by storing an externally mutable object into HotelConfig.hotel [gr2116.RESTservice.restserver.HotelConfig] At HotelConfig.java:[line 54] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.RESTservice.restserver.HotelITConfig.getHotel() may expose internal representation by returning HotelITConfig.hotel [gr2116.RESTservice.restserver.HotelITConfig] At HotelITConfig.java:[line 63] EI_EXPOSE_REP
[ERROR] Medium: gr2116.RESTservice.restserver.HotelITConfig.setHotel(Hotel) may expose internal representation by storing an externally mutable object into HotelITConfig.hotel [gr2116.RESTservice.restserver.HotelITConfig] At HotelITConfig.java:[line 54] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.RESTservice.restserver.HotelModuleObjectMapperProvider.getContext(Class) may expose internal representation by returning HotelModuleObjectMapperProvider.objectMapper [gr2116.RESTservice.restserver.HotelModuleObjectMapperProvider] At HotelModuleObjectMapperProvider.java:[line 26] EI_EXPOSE_REP
[INFO] 
