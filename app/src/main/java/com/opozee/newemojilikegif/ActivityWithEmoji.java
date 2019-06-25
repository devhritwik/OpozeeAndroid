package com.opozee.newemojilikegif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;



import com.opozee.newemojilikegif.EmojiConfig;
import com.opozee.newemojilikegif.EmojiLikeTouchDetector;
import com.opozee.newemojilikegif.IActivityWithEmoji;


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
