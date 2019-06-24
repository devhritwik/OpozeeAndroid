package com.opozee.testing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.opozee.OpozeeActivity;
import com.opozee.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.andreidobrescu.emojilike.Emoji;
import ro.andreidobrescu.emojilike.EmojiConfig;
import ro.andreidobrescu.emojilike.EmojiLikeTouchDetector;
import ro.andreidobrescu.emojilike.EmojiLikeView;
import ro.andreidobrescu.emojilike.IActivityWithEmoji;
import ro.andreidobrescu.emojilike.OnEmojiSelectedListener;

public class MainTestActivity extends OpozeeActivity implements OnEmojiSelectedListener, IActivityWithEmoji {

    EmojiLikeTouchDetector emojiLikeTouchDetector;

    @BindView(R.id.likeButton)
    ImageView likeButton;

    @BindView(R.id.emojiView)
    EmojiLikeView emojiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        ButterKnife.bind(this);

        emojiLikeTouchDetector=new EmojiLikeTouchDetector();

        EmojiConfig.with(this)
                .on(likeButton)
                .open(emojiView)
                .addEmoji(new Emoji(R.drawable.resizelike, "Like"))
                .addEmoji(new Emoji(R.drawable.haha, "Haha"))
                .addEmoji(new Emoji(R.drawable.kiss, "Kiss"))
//                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
//                .addEmoji(new Emoji(R.drawable.t, ":P"))
                .setEmojiViewInAnimation((AnimationSet) AnimationUtils.loadAnimation(this, R.anim.in_animation))
                .setEmojiViewOutAnimation((AnimationSet) AnimationUtils.loadAnimation(this, R.anim.out_animation))
                .setBackgroundImage(R.drawable.background_drawable)
                .setOnEmojiSelectedListener(this)
                .setup();
    }

    @Override
    public void onEmojiSelected(Emoji emoji)
    {
        Toast.makeText(this, "Selected "+emoji.getDescription(), Toast.LENGTH_SHORT).show();
    }

//    @OnClick(R.id.fragmentDemo)
//    public void fragmentDemo ()
//    {
//        Intent i=new Intent(this, FragmentActivitySample.class);
//        startActivity(i);
//    }

    @OnClick(R.id.recyclerDemo)
    public void recyclerDemo ()
    {
        Intent i=new Intent(this, TestingActivity.class);
        startActivity(i);
    }

    //override theese 2 methods if your activity doesn't extend ActivityWithEmoji
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
