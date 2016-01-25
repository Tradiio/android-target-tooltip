package it.sephiroth.android.library.tooltip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by rsantos on 19/01/16.
 */
public class TooltipFramelayout extends FrameLayout {

    Tooltip.Gravity mGravity;
    Point mAnchorPoint;
    Rect mRect;
    Rect mAnchorRect;

    int[] thisPosition = new int[2];
    int[] anchorPosition = new int[2];

    int arrowSide;
    int arrowMargin;
    Paint mPaint = new Paint();

    public TooltipFramelayout(Context context, AttributeSet attrs){
        super(context, attrs);
        setWillNotDraw(false);
        arrowSide = Utils.convertDpToPixel(8,context);
        arrowMargin = Utils.convertDpToPixel(2,context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mGravity != null && mAnchorPoint != null){

            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(2);
            mPaint.setColor(Color.WHITE);
            Path path = new Path();

//            Log.d("Oi", "Anchor Point X: " + mAnchorPoint.x);

            if(mGravity == Tooltip.Gravity.BOTTOM){
                path.moveTo(mAnchorPoint.x , 0);
                path.lineTo(mAnchorPoint.x + (arrowSide-arrowMargin), arrowSide);
                path.lineTo(mAnchorPoint.x - (arrowSide-arrowMargin), arrowSide);
            }

            if(mGravity == Tooltip.Gravity.TOP){
                path.moveTo(mAnchorPoint.x , getHeight());
                path.lineTo(mAnchorPoint.x - (arrowSide-arrowMargin) , getHeight() - arrowSide);
                path.lineTo(mAnchorPoint.x + (arrowSide-arrowMargin) , getHeight() - arrowSide);
            }

            if(mGravity == Tooltip.Gravity.LEFT) {
                path.moveTo(getWidth() , mAnchorPoint.y + arrowSide);
                path.lineTo(getWidth() - arrowSide , mAnchorPoint.y + (arrowSide-arrowMargin));
                path.lineTo(getWidth() - arrowSide , mAnchorPoint.y - (arrowSide-arrowMargin));
            }

            if(mGravity == Tooltip.Gravity.RIGHT) {
                path.moveTo(0 , mAnchorPoint.y);
                path.lineTo(arrowSide, mAnchorPoint.y - (arrowSide-arrowMargin));
                path.lineTo(arrowSide, mAnchorPoint.y + (arrowSide-arrowMargin));
            }

            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    public void setAnchor(View v,Tooltip.Gravity gravity){
        this.mGravity = gravity;

        this.getLocationOnScreen(thisPosition);
        v.getLocationOnScreen(anchorPosition);

        mRect = new Rect(thisPosition[0],thisPosition[1],thisPosition[0]+getWidth(),thisPosition[1]+getHeight());
        mAnchorRect = new Rect(anchorPosition[0],anchorPosition[1],anchorPosition[0]+v.getWidth(),anchorPosition[1]+v.getHeight());

//        Log.d("Oi","----- Esta Vista -----");
//        Log.d("Oi","Left: "+mRect.left);
//        Log.d("Oi","Top: "+mRect.top);
//        Log.d("Oi","Right: "+mRect.right);
//        Log.d("Oi","Bottom: "+mRect.bottom);
//        Log.d("Oi","----- Vista Anchora -----");
//        Log.d("Oi","Left: "+mAnchorRect.left);
//        Log.d("Oi","Top: "+mAnchorRect.top);
//        Log.d("Oi","Right: "+mAnchorRect.right);
//        Log.d("Oi","Bottom: "+mAnchorRect.bottom);
//
//        Log.d("Oi","Primeira opção centerX :1 - "+mAnchorRect.centerX()+"      2 - "+(mAnchorRect.left + (mAnchorRect.right - mAnchorRect.left)/2));
//        Log.d("Oi","Segunda opção centerX :1 - "+mRect.centerX()+"     2 - " +(mAnchorRect.left + (mRect.right - mRect.left)/2));

        int x;
        int y;

        if(mRect.left <= mAnchorRect.left) {
            x = mAnchorRect.left - mRect.left + (mAnchorRect.right - mAnchorRect.left)/2;
        } else {
            x = mAnchorRect.left + (mRect.right - mRect.left)/2;
        }

        if(mRect.top <= mAnchorRect.top){
            y = mAnchorRect.top - mRect.top + (mAnchorRect.bottom - mAnchorRect.top)/2;
        } else {
            y = mAnchorRect.top + (mRect.bottom - mRect.top)/2;
        }

        mAnchorPoint = new Point(x,y);

//        Log.d("Oi","Centro = "+mAnchorPoint.toString());
    }

}
