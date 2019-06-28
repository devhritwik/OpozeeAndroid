package com.opozeeApp.testing;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.opozeeApp.OpozeeActivity;
import com.opozeeApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.opozeeApp.newemojilikegif.Emoji;
import com.opozeeApp.newemojilikegif.EmojiCellView;
import com.opozeeApp.newemojilikegif.EmojiConfig;
import com.opozeeApp.newemojilikegif.EmojiLikeTouchDetector;
import com.opozeeApp.newemojilikegif.EmojiLikeView;
import com.opozeeApp.newemojilikegif.IActivityWithEmoji;
import com.opozeeApp.newemojilikegif.OnEmojiSelectedListener;

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
                .addEmoji(new Emoji(R.drawable.like, "Like"))
                .addEmoji(new Emoji(R.drawable.haha, "Haha"))
                .addEmoji(new Emoji(R.drawable.kiss, "Kiss"))
                .setEmojiAnimationSpeed(0.2f)
                .setEmojiCellViewFactory(EmojiCellView.WithImageAndText::new)
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
