package group_0617.csc207.gamecentre.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import group_0617.csc207.gamecentre.GenericBoardManager;
import group_0617.csc207.gamecentre.game2048.Game2048Activity;

/**
 * Adapted from:
 * https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java
 * <p>
 * This extension of GridView contains built in logic for handling swipes between buttons
 * excluded from tests because it's a /class
 */
public class GestureDetectGridView extends GridView {
    public static final int SWIPE_MIN_DISTANCE = 100;
    public static final int SWIPE_THRESHOLD_VELOCITY = 25;

    private GestureDetector gDetector;
    private MovementController mController;
    private boolean mFlingConfirmed = false;
    private boolean ableToFling = false;
    private float mTouchX;
    private float mTouchY;

    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position);
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (ableToFling) {
                    int direction = 0;

                    if (Math.abs(e1.getY() - e2.getY()) > Math.abs(e1.getX() - e2.getX())) {
                        if (Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                            return false;
                        }
                        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                            direction = Game2048Activity.UP;
                        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                            direction = Game2048Activity.DOWN;
                        }
                    } else {
                        if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                            return false;
                        }
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                            direction = Game2048Activity.LEFT;
                        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                            direction = Game2048Activity.RIGHT;
                        }
                    }
                    mController.processTapMovement(context, direction);
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (mFlingConfirmed) {
                return true;
            }
            float dX = ev.getX() - mTouchX;
            float dY = ev.getY() - mTouchY;
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    public void setGenericBoardManager(GenericBoardManager inGenericBoardManager) {
        mController.setGenericBoardManager(inGenericBoardManager);
    }

    public void setAbleToFling(boolean ableToFling) {
        this.ableToFling = ableToFling;
    }
}
