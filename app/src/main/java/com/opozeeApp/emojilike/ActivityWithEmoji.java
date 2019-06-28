package com.opozeeApp.emojilike;

import android.os.Bundle;
import android.view.MotionEvent;

import com.opozeeApp.OpozeeActivity;

public class ActivityWithEmoji extends OpozeeActivity implements IActivityWithEmoji
{
    EmojiLikeTouchDetector emojiLikeTouchDetector;

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
    public void configureEmojiLike(EmojiConfig config) {
        emojiLikeTouchDetector.configure(config);
    }
}

