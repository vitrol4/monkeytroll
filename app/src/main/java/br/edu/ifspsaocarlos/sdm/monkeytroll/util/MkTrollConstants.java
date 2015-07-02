package br.edu.ifspsaocarlos.sdm.monkeytroll.util;

/**
 * Created by victor on 09/06/15.
 */
public class MkTrollConstants {
    public static final String PREFER = "ConciergePrefs";
    public static String ENDPOINT = "https://api.bemygue.st/api";
    public static String AFFILIATE_REWARD = "/rewards/affiliate";
    public static String CITIES = "/cities";
    public static String EVENTS = "/events";
    public static String ORDERS = "/orders";
    public static String PARTIES = "/parties";
    public static String PLACES = "/places";
    public static String TICKET_TYPE = "/tickettypes";
    public static String TICKETS = "/tickets";
    public static String USERS = "/users";
    public static String AUTHORIZE_PARTNER = "/authorize/partner";
    public static String HEADER_ACCESS_TOKEN = "Access-Token";
    public static String HEADER_REQUEST_TYPE = "Request-type";
    public static String HEADER_MAINTENANCE = "MAINTENANCE";
    public static String PARAM_CITY = "city";
    public static String PARAM_EMAIL = "email";
    public static String PARAM_PASSWORD = "password";
    public static String PARAM_PARTY_ID = "partyId";
    public static String PARAM_PARTY_USED = "used";

    // Database
    public static class DataBase {
        public static final String DATABASE_NAME = "Concierge";
        public static final int DATABASE_VERSION = 1;
    }

    // ****************************TABELAS*******************************//
    public static class Ticket {
        public static final String TABLE_NAME = "Ticket";
        public static final String _ID = "_id";
        public static final String TICKET_ID = "ticket_id";
        public static final String GUEST_NAME = "guest_name";
        public static final String GUEST_RG = "guest_rg";
        public static final String CODE = "code";
        public static final String SENT = "sent";
        public static final String USED = "used";
    }

    public static class Party {
        public static final String TABLE_NAME = "Party";
        public static final String _ID = "_id";
        public static final String PARTY_ID = "party_id";
        public static final String NAME = "name";
        public static final String DATE = "date";
        public static final String ADDRESS = "address";

    }

}
