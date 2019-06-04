package com.opozee.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.opozee.R;
import com.opozee.activities.HomeActivity;
import com.opozee.activities.QuestionDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class FCMMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            handleNow(remoteMessage.getData().get("body"));


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            handleNow(remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     * @param data
     */
    private void handleNow(String data) {
//        {"message": "WebApp123 Solution Has given opinion on your Question.","title": "Oposee App", "type" : "QD","questId":"31" }

        try {
            JSONObject json = new JSONObject(data);
            Type type = Type.HOME;
            if(json.getString("type").equals("QD")) {
                type = Type.QUES_DETAIL;
            }

            setIntent(json.getString("message"), json.getInt("questId"), R.mipmap.question_icon, type);

            Log.d(TAG, "Short lived task is done.");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message FCM message body received.
     * @param questId FCM questId received whenever user tags,comment, likes, dislikes then this var will come.
     * @param question_icon FCM question_icon this is the icon shown in notification.
     * @param type FCM TYpe will be set accordingly.
     *
     */
    private void setIntent(String message, int questId, int question_icon, Type type) {
        Intent i = null;
        switch (type)
        {

            case HOME:
                i = new Intent(this, HomeActivity.class);
                sendNotification(message, question_icon, i);
                break;

            case QUES_DETAIL:
                i = new Intent(this, QuestionDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putInt("id", questId);
                bundle.putString("from", "notification");
                i.putExtras(bundle);
                sendNotification(message, question_icon, i);
                break;

                default:
                    i = new Intent(this, HomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    sendNotification(message, question_icon, i);
                    break;
        }
    }

    enum Type{
        QUES_DETAIL,
        HOME
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.



    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message FCM message body received.
     * @param question_icon FCM question_icon this is the icon shown in notification.
     * @param i FCM intent will be set accordingly.
     *
     */
    private void sendNotification(String message, int question_icon, Intent i) {
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.question_icon)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
