package com.example.sportivesandroid.Utils;

public class Tags {

    public static final String ONESIGNAL_APP_ID = "ad1d7216-f430-4929-a719-2c31195e798a";
    public static final String NUEVA ="nueva" ;
    public static final String ANTIGUA ="antigua" ;

    /**Id tag for  maquina*/
    public static final String ID_MAQUINA ="id_maquina" ;

    /**Name tag for multiple uses */
    public static final String NOMBRE = "nombre";

    /**Name tag maquina*/
    public static final String NOMBRE_MAQUINA = "nombre_maquina";
    /**Date tag */
    public static final String FECHA = "fecha";
    /**Time tag */
    public static final String HORA = "hora";
    /**Use time tag */
    public static final String TIEMPO_USO = "tiempo_uso";
    /**List tag */
    public static final String LISTA = "lista";
    /**Card tag */
    public static final String TARJETA = "tarjeta";
    /**NoCard tag */
    public static final CharSequence NO_VISA = "no_cards";
    /**URL_GUARDAR_TARJETA tag its a call but now made in Functions
     * @see Functions creartarjeta
     * */
    public static final String URL_GUARDAR_TARJETA = "/api/v1/usuarios/guardar_tarjeta/";
    /**Url tag */
    public static final String URL = "url";
    /**server  tag is the url domain*/
    public static String SERVIDOR = "http://192.168.1.45:8090/";

    /**media  tag is to complete the url and take media files*/
    public static String MEDIA = SERVIDOR +"static/media/";
    /**User tags */
    public static String USER = "usuario",
            USERNAME = "username",
            LASTNAME = "apellidos",
            PHONE = "telefono",
            PHOTO = "foto",
            NAME = "nombre",
            EMAIL ="email",
            DESCRIPTION = "description",
            OSREGISTRATION = "os_registration",
            OSUSER = "os_user";

    /**Security tags */
    public static final String TOKEN = "token",
            TOKEN_GOOGLE = "token_google",
            PK = "pk",
            TIPO_USUARIO = "user",
            TIPO_SESION = "tipo_sesion",
            PASSWORD = "password",
            RESULT = "result",
            OK = "ok",
            CODE_OK = "code_ok",
            MESSAGE = "message",
            USER_ID = "usuario_id",
            IMAGE = "imagen",
            LOGGED_IN = "Usuario logueado",
            REGISTERED_USER = "Usuario creado correctamente",
            NOT_LOGGED_IN = "Usuario no logueado";

    public static final String CLIENT_SECRET = "client_secret";
    /**STRIPE_PUBLIC_KEY  tag is pk_test from stripe account*/
    public static String STRIPE_PUBLIC_KEY = "pk_test_51IYyXyKhOFEV5aAVYobT4WsyQVt451tfr0SG3YQZl80H7G2tIxZUBQImXTgMb73RC6e4CgpdfbDt1vokfjjrqGe000j1XBaPdc";
}
