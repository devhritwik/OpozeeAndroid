package com.opozeeApp.utils;


public class AppGlobal {

    public static final String FCM_ID = "fcm_id";
   public static final String EXTRA_DATA = "notification_data";


    /*******************************************************
     * UserProfile With Key to save values
     *******************************************************/

    public static final String USER_ID = "UserId";// int
    public static final String FirstName = "FirstName";// String
    public static final String LastName = "LastName";// String
    public static final String UserName = "UserName";// String
    public static final String Email = "Email";// String
    public static final String Photo = "Photo";// String
    public static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String SORT_ORDER = "sort_order";
    public static final String IDPROFILEGET = "id_profile_get";

    //Types of API calls
    public static final int TYPE_PROFILE_UPDATE = 2;
    public static final int TYPE_GET_PROFILE = 3;
    public static final String KEY_USER_PROFILE_PIC = "Image";


    public static String FACEBOOK = "0";
    public static String GOOGLE = "1";
    public static String TWITTER = "2";


    public static final String REGISTRATION_ID = "registrationId";// String


/*********************  TYPES **********************/
//this is used to save the last visible fragment when ever user navigates to QuestionDetailActivity in order refresh the list
public static String LAST_FRAG_TYPE = "last_visible_frag";

 /*********************************************************
  * Fragment Types
  *********************************************************/
  public static final int PROFILEFRAG = 1;
  public static final int NOTIFICATIONFRAG = 2;
 public static final int SEARCHFRAG = 3;
 public static final int FAVOURITEFRAG = 4;
  public static final int HOMEFRAG = 5;
    public static final int POSTQUESTFRAG = 6;

    public static final int  USERPOSTS = 7 ;



    public static String sharedPrefName = "Oposee_App_Pref";
}