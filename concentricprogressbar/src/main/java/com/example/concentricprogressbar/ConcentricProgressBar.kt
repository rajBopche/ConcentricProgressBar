package com.example.concentricprogressbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator


class ConcentricProgressBar(context: Context, attributeSet: AttributeSet) :
        View(context, attributeSet) {


    companion object {
        private const val DEFAULT_INNER_PROGRESS_COLOR = Color.BLUE
        private const val DEFAULT_OUTER_PROGRESS_COLOR = Color.CYAN
    }

    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var innerSweepAngle = 360f
    private var outerSweepAngle = 360f

    private var outerStartAngle = 0f
    private var innerStartAngle = 0f

    private var outerRectHeight = 120f
    private var outerRectWidth = 120f

    private var innerRectEndWidth = 0f
    private var innerRectEndHeight = 0f
    private var innerRectStartWidth = 0f
    private var innerRectStartHeight = 0f

    private lateinit var outerRect: RectF
    private lateinit var innerRect: RectF

    private var innerProgressColor = DEFAULT_INNER_PROGRESS_COLOR
    private var outerProgressColor = DEFAULT_OUTER_PROGRESS_COLOR


    init {

        val typedArray = context.obtainStyledAttributes(
                attributeSet, R.styleable.ConcentricProgressBar,
                0, 0
        )

        innerProgressColor = typedArray.getColor(
                R.styleable.ConcentricProgressBar_inner_progress_color,
                DEFAULT_INNER_PROGRESS_COLOR
        )
        outerProgressColor = typedArray.getColor(
                R.styleable.ConcentricProgressBar_outer_progress_color,
                DEFAULT_OUTER_PROGRESS_COLOR
        )
        typedArray.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var finalHeight = 0f
        var finalWidth = 0f

        val desiredHeight =
                outerRectHeight  //this height should match the height of outer rectangle
        val desiredWidth = outerRectWidth   //this width should match the width of outer rectangle


        val widthMode = MeasureSpec.getMode(widthMeasureSpec) //check mode for height
        val heightMode = MeasureSpec.getMode(heightMeasureSpec) //check mode for width

        val widthSize: Float = MeasureSpec.getSize(widthMeasureSpec).toFloat()  // passed in xml
        val heightSize: Float = MeasureSpec.getSize(heightMeasureSpec).toFloat()  //passed in xml

        when (widthMode) {
            MeasureSpec.EXACTLY -> finalWidth = widthSize //set to whatever passed from xml

            MeasureSpec.AT_MOST -> finalWidth =
                    desiredWidth.coerceAtMost(widthSize)  //cannot be greater than received width

            MeasureSpec.UNSPECIFIED -> finalWidth =
                    desiredWidth // view width can be anything, so we want it be of desired width
        }

        when (heightMode) {
            MeasureSpec.EXACTLY -> finalHeight = heightSize

            MeasureSpec.AT_MOST -> finalHeight = desiredHeight.coerceAtMost(heightSize)

            MeasureSpec.UNSPECIFIED -> finalHeight = desiredHeight

        }

        outerRectHeight = finalHeight
        outerRectWidth = finalWidth
        setInnerRectMeasure()
        setMeasuredDimension(
                finalWidth.toInt() + 10, finalHeight.toInt() + 10
        )  //plus 10 is added to accomodate for stroke width of circle
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawOuterRect(canvas)
        drawInnerRect(canvas)
        drawInnerProgressBar(canvas)
        drawOuterProgressBar(canvas)
    }

    private fun setInnerRectMeasure() {
        innerRectStartWidth = (outerRectWidth / 4)
        innerRectStartHeight = (outerRectHeight / 4)

        innerRectEndWidth = (outerRectWidth / 2) + (outerRectWidth / 4)
        innerRectEndHeight = (outerRectHeight / 2) + (outerRectHeight / 4)
    }

    private fun drawOuterRect(canvas: Canvas?) {
        paint.apply {
            color = Color.TRANSPARENT
            style = Paint.Style.STROKE
        }
        outerRect = RectF(10f, 10f, outerRectWidth, outerRectHeight)
        canvas?.drawRect(outerRect, paint)
    }

    private fun drawInnerRect(canvas: Canvas?) {
        paint.apply {
            color = Color.TRANSPARENT
            style = Paint.Style.STROKE
        }
        innerRect = RectF(
                innerRectStartWidth + 10f,
                innerRectStartHeight + 10f,
                innerRectEndWidth,
                innerRectEndHeight
        )
        canvas?.drawRect(innerRect, paint)
    }

    private fun drawInnerProgressBar(canvas: Canvas?) {
        paint.color = innerProgressColor
        paint.strokeWidth = 15f
        canvas?.drawArc(innerRect, innerStartAngle, -innerSweepAngle, false, paint)
    }

    private fun drawOuterProgressBar(canvas: Canvas?) {
        paint.color = outerProgressColor
        paint.strokeWidth = 15f
        canvas?.drawArc(outerRect, outerStartAngle, outerSweepAngle, false, paint)
    }

    private fun animateProgressBar() {
        val animator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 1000
            interpolator = DecelerateInterpolator(1f)
        }
        animator.start()
        animator.addUpdateListener {
            innerStartAngle = -innerSweepAngle
            outerStartAngle = outerSweepAngle

            innerSweepAngle = it.animatedValue as Float
            outerSweepAngle = it.animatedValue as Float
            invalidate()
        }

        animator.repeatCount = ValueAnimator.INFINITE
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animateProgressBar() //startAnimation
    }
}
