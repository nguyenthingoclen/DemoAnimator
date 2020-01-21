package com.example.demoanimator

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter.createFromResource(this,R.array.anim_options,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        anim_spinner.adapter= adapter
        (anim_spinner as Spinner).onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            1 -> {
                translate()
            }
            2 -> {
                rotate()
            }
            3 -> {
                fade()
            }
            4 -> {
                scale()
            }
            5 -> {
                combin_anims()
            }
            6 -> {
                valueAnim()
            }
        }

    }

    // di chuyen theo x y
    private fun translate(){
        //val mTranslate = ObjectAnimator.ofFloat(star,View.TRANSLATION_X,0f,200f)
        val mTranslate = ObjectAnimator.ofFloat(star,View.TRANSLATION_Y,0f,200f)
        mTranslate.duration = 1000
        mTranslate.interpolator = BounceInterpolator()
        mTranslate.start()
    }

    //xoay quanh mình
    private fun rotate(){
        val mRotate = ObjectAnimator.ofFloat(star,View.ROTATION,-360f,0f)
        mRotate.duration = 1000
        mRotate.interpolator = AccelerateInterpolator()
        mRotate.start()
    }

    //làm đậm ở mờ dần
    private fun fade(){
        // đậm
        val mFade = ObjectAnimator.ofFloat(star,View.ALPHA,0.2f,1f)
        //mờ
      //  val mFade = ObjectAnimator.ofFloat(star,View.ALPHA,2f,0f)
        mFade.duration = 2000
        mFade.start()
    }
    // phong to và thu nhỏ
    private fun scale(){
        val mScale = AnimatorSet()
        val sX = ObjectAnimator.ofFloat(star,View.SCALE_X,0.2f,1.0f)
        val sY = ObjectAnimator.ofFloat(star,View.SCALE_Y,0.2f,1.0f)
        mScale.playTogether(sX,sY)
        mScale.start()
    }

    //mix
    private fun combin_anims(){

        val anims1 = AnimatorSet()
        val sX = ObjectAnimator.ofFloat(star,View.SCALE_X,0.2f,1f)
        val sY = ObjectAnimator.ofFloat(star,View.SCALE_Y,0.2f,1f)
        val fade = ObjectAnimator.ofFloat(star,View.ALPHA,0.2f,1f)
        anims1.playTogether(sX,sY,fade)

        val anims2 = AnimatorSet()
        val tx1= ObjectAnimator.ofFloat(star,View.TRANSLATION_Y,0f,200f)
        tx1.duration = 1000
        tx1.interpolator = BounceInterpolator()
        val rotate = ObjectAnimator.ofFloat(star,View.ROTATION,-360f,0f)
        rotate.duration = 1000
        rotate.interpolator = AccelerateInterpolator()
        anims2.playTogether(tx1,rotate)

        val finalAnim = AnimatorSet()
        finalAnim.play(anims1).before(anims2)
        //finalAnim.play(anims2)

        finalAnim.addListener(object  :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
                Toast.makeText(applicationContext,"Animation Repeat",Toast.LENGTH_SHORT).show();

            }

            override fun onAnimationEnd(animation: Animator?) {
                Toast.makeText(applicationContext,"Animation Fininsed",Toast.LENGTH_SHORT).show();

            }

            override fun onAnimationCancel(animation: Animator?) {
                Toast.makeText(applicationContext,"Animation Cancel",Toast.LENGTH_SHORT).show();

            }

            override fun onAnimationStart(animation: Animator?) {
                Toast.makeText(applicationContext,"Animation Started",Toast.LENGTH_SHORT).show();

            }

        })
        finalAnim.start()
    }

    //set theo value
    private fun valueAnim(){
        val tx = ValueAnimator.ofFloat(200f, 0f)
        val mDuration = 1000 //in millis
        tx.duration = mDuration.toLong()
        tx.addUpdateListener { animation -> star!!.translationX = animation.animatedValue as Float }
        tx.start()
    }

}
