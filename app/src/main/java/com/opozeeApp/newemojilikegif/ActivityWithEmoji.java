package com.opozeeApp.newemojilikegif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;


public class ActivityWithEmoji extends AppCompatActivity implements IActivityWithEmoji
{
    private EmojiLikeTouchDetector emojiLikeTouchDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        emojiLikeTouchDetector=new EmojiLikeTouchDetector();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        boolean shouldCallSuper=emojiLikeTouchDetector.dispatchTouchEvent(event);
        if (shouldCallSuper)
            return super.dispatchTouchEvent(event);
        return false;
    }

    @Override
    public void configureEmojiLike(EmojiConfig config)
    {
        emojiLikeTouchDetector.configure(config);
    }
}
