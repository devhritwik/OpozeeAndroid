package com.opozeeApp.newemojilikegif;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;


public class EmojiTriggerManager
{
    private View triggerView;

    private EmojiLikeView emojiView;

    private int touchDownDelay;

    private int touchUpDelay;

    private boolean triggerViewTouched=false;

    private boolean shouldSendEventsToEmojiView=false;

    private MotionEvent downEvent;

    private boolean shouldWaitForClosing=false;

    private MotionEvent upEvent;

    public void configure (EmojiConfig config)
    {
        this.triggerView=config.triggerView;
        this.touchDownDelay=config.touchDownDelay;
        this.touchUpDelay=config.touchUpDelay;
        this.emojiView=config.emojiView;
    }

    private boolean intersectView (View view, int rx, int ry)
    {
        int[] l = new int[2];
        view.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        int w = view.getWidth();
        int h = view.getHeight();
        return rx >= x && rx <= x + w && ry >= y && ry <= y + h;
    }

    public boolean onTouch (MotionEvent event)
    {
        float x=event.getRawX();
        float y=event.getRawY();
        int action=event.getAction();

        //if the emoji view is closing (when the user releases the touch), wait for the closing to be done
        if (shouldWaitForClosing)
            return true;

        if (action==MotionEvent.ACTION_DOWN)
        {
            if (intersectView(triggerView, (int)x, (int)y))
            {
                triggerViewTouched=true;
                shouldSendEventsToEmojiView=false;
                downEvent=event;

                new Handler().postDelayed(() ->
                {
                    if (triggerViewTouched)
                    {
                        //if the user has touched and the touch is still down
                        shouldSendEventsToEmojiView=true;
                        emojiView.onTouchDown(downEvent.getRawX(), downEvent.getRawY());
                        emojiView.show();
                    }
                }, touchDownDelay);

                return false;
            }
        }
        else if (action==MotionEvent.ACTION_MOVE)
        {
            if (triggerViewTouched)
            {
                if (shouldSendEventsToEmojiView)
                {
                    emojiView.onTouchMove(x, y);
                    return false;
                }
            }
        }
        else if (action==MotionEvent.ACTION_UP)
        {
            triggerViewTouched=false;
            shouldSendEventsToEmojiView=false;
            shouldWaitForClosing=true;
            upEvent=event;

            new Handler().postDelayed(() ->
            {
                shouldWaitForClosing=false;
                emojiView.onTouchUp(upEvent.getRawX(), upEvent.getRawY());
                emojiView.hide();
            }, touchUpDelay);

            return false;
        }

        return true;
    }

    public EmojiLikeView getEmojiView()
    {
        return emojiView;
    }
}
