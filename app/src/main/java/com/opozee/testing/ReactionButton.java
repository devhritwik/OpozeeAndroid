package com.opozee.testing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.opozee.R;

public class ReactionButton extends AppCompatActivity {

    private ReactionView reactionView;
//    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_button);
    }
//    private void init() {
////        reactionView = (ReactionView) findViewById(R.id.reaction);
//        button = (Button) findViewById(R.id.button);
//
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                reactionView.show(motionEvent);
//                return false;
//            }
//        });
//    }
}
