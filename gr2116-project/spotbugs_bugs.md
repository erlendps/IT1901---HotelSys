??:
*****************
[ERROR] Medium: gr2116.persistence.HotelPersistence.loadHotel() may fail to clean up java.io.Reader on checked exception [gr2116.persistence.HotelPersistence, gr2116.persistence.HotelPersistence, gr2116.persistence.HotelPersistence, gr2116.persistence.HotelPersistence, gr2116.persistence.HotelPersistence] Obligation to clean up resource created at HotelPersistence.java:[line 106] is not dischargedPath continues at HotelPersistence.java:[line 109]Path continues at HotelPersistence.java:[line 112]Path continues at HotelPersistence.java:[line 113]Path continues at HotelPersistence.java:[line 114] OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE
*****************

Suppress:
*****************
[ERROR] High: Random object created and used only once in gr2116.persistence.RoomGenerator.generateRooms(int) [gr2116.persistence.RoomGenerator] At RoomGenerator.java:[line 55] DMI_RANDOM_USED_ONLY_ONCE
[ERROR] High: Random object created and used only once in gr2116.persistence.RoomGenerator.generateRooms(int) [gr2116.persistence.RoomGenerator, gr2116.persistence.RoomGenerator] At RoomGenerator.java:[line 44]Another occurrence at RoomGenerator.java:[line 58] DMI_RANDOM_USED_ONLY_ONCE
[ERROR] High: Random object created and used only once in gr2116.persistence.RoomGenerator.getRandomNumber(int, int) [gr2116.persistence.RoomGenerator] At RoomGenerator.java:[line 138] DMI_RANDOM_USED_ONLY_ONCE
[ERROR] High: Random object created and used only once in gr2116.persistence.RoomGenerator.getRandomRoomType() [gr2116.persistence.RoomGenerator] At RoomGenerator.java:[line 78] DMI_RANDOM_USED_ONLY_ONCE
*****************

Fixed?:
*****************
[ERROR] High: Self comparison of jsonNode with itself gr2116.persistence.internal.HotelDeserializer.deserialize(JsonNode) [gr2116.persistence.internal.HotelDeserializer] At HotelDeserializer.java:[line 36] SA_LOCAL_SELF_COMPARISON
[ERROR] High: Self comparison of jsonNode with itself gr2116.persistence.internal.PersonDeserializer.deserialize(JsonNode) [gr2116.persistence.internal.PersonDeserializer] At PersonDeserializer.java:[line 39] SA_LOCAL_SELF_COMPARISON
[ERROR] High: Self comparison of jsonNode with itself gr2116.persistence.internal.ReservationDeserializer.deserialize(JsonNode) [gr2116.persistence.internal.ReservationDeserializer] At ReservationDeserializer.java:[line 42] SA_LOCAL_SELF_COMPARISON
[ERROR] High: Self comparison of jsonNode with itself gr2116.persistence.internal.RoomDeserializer.deserialize(JsonNode) [gr2116.persistence.internal.RoomDeserializer] At RoomDeserializer.java:[line 47] SA_LOCAL_SELF_COMPARISON
******************

Suppress (They must?):
*****************
[ERROR] Medium: new gr2116.ui.access.DirectHotelAccess(HotelPersistence) may expose internal representation by storing an externally mutable object into DirectHotelAccess.hotelPersistence [gr2116.ui.access.DirectHotelAccess] At DirectHotelAccess.java:[line 21] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.controller.AppController.getHotelAccess() may expose internal representation by returning AppController.hotelAccess [gr2116.ui.controller.AppController] At AppController.java:[line 221] EI_EXPOSE_REP
[ERROR] Medium: gr2116.ui.controller.AppController.getHotelPersistence() may expose internal representation by returning AppController.hotelPersistence [gr2116.ui.controller.AppController] At AppController.java:[line 230] EI_EXPOSE_REP
[ERROR] Medium: gr2116.ui.controller.AppController.setHotelAccess(HotelAccess) may expose internal representation by storing an externally mutable object into AppController.hotelAccess [gr2116.ui.controller.AppController] At AppController.java:[line 247] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.front.FrontPageController.getLoginPanelViewController() may expose internal representation by returning FrontPageController.loginPanelViewController [gr2116.ui.front.FrontPageController] At FrontPageController.java:[line 65] EI_EXPOSE_REP
[ERROR] Medium: gr2116.ui.front.FrontPageController.getSignUpPanelViewController() may expose internal representation by returning FrontPageController.signUpPanelViewController [gr2116.ui.front.FrontPageController] At FrontPageController.java:[line 69] EI_EXPOSE_REP
[ERROR] Medium: new gr2116.ui.main.HotelRoomListItem(HotelRoom) may expose internal representation by storing an externally mutable object into HotelRoomListItem.room [gr2116.ui.main.HotelRoomListItem] At HotelRoomListItem.java:[line 52] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.main.MainPageController.setHotelAccess(HotelAccess) may expose internal representation by storing an externally mutable object into MainPageController.hotelAccess [gr2116.ui.main.MainPageController] At MainPageController.java:[line 71] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.main.MainPageController.setPerson(Person) may expose internal representation by storing an externally mutable object into MainPageController.person [gr2116.ui.main.MainPageController] At MainPageController.java:[line 88] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.main.UserPanelController.setPerson(Person) may expose internal representation by storing an externally mutable object into UserPanelController.person [gr2116.ui.main.UserPanelController] At UserPanelController.java:[line 57] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.money.MoneyPageController.setHotelAccess(HotelAccess) may expose internal representation by storing an externally mutable object into MoneyPageController.hotelAccess [gr2116.ui.money.MoneyPageController] At MoneyPageController.java:[line 56] EI_EXPOSE_REP2
[ERROR] Medium: gr2116.ui.money.MoneyPageController.setPerson(Person) may expose internal representation by storing an externally mutable object into MoneyPageController.person [gr2116.ui.money.MoneyPageController] At MoneyPageController.java:[line 42] EI_EXPOSE_REP2
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
*****************





