/*******************************************************************************
 * Copyright 2015 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.ys.yoosir.liveanimation.showCircleBitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.L;

/**
 * Can display bitmap cropped by a circle. This implementation works only with ImageViews wrapped
 * in ImageViewAware.
 * <br />
 * If this implementation doesn't meet your needs then consider
 * <a href="https://github.com/vinc3m1/RoundedImageView">RoundedImageView</a> or
 * <a href="https://github.com/Pkmmte/CircularImageView">CircularImageView</a> projects for usage.
 *
 * @author Qualtagh, Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.9.5
 */
public class RectangleBitmapDisplayer implements BitmapDisplayer {

	protected final Integer strokeColor;
	protected final float strokeWidth;
	protected final float mRadius;

	public RectangleBitmapDisplayer() {
		this(null);
	}

	public RectangleBitmapDisplayer(Integer strokeColor) {
		this(strokeColor, 0);
	}

	public RectangleBitmapDisplayer(Integer strokeColor, float strokeWidth) {
		this(strokeColor, 0,0);
	}

	public RectangleBitmapDisplayer(Integer strokeColor, float strokeWidth,float radius) {
		this.strokeColor = strokeColor;
		this.strokeWidth = strokeWidth;
		this.mRadius = radius;
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
		if (!(imageAware instanceof ImageViewAware)) {
			throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
		}

		imageAware.setImageDrawable(new RectangleDrawable(bitmap, strokeColor, strokeWidth,mRadius));
	}

	public static class RectangleDrawable extends Drawable {

		protected float radius;

		protected final RectF mRect = new RectF();
		protected RectF mRoundRect;
		protected RectF mStrokeRoundRect;
		protected final RectF mBitmapRect;
		protected final BitmapShader bitmapShader;
		protected final Paint paint;
		protected final Paint strokePaint;
		protected final float strokeWidth;
		protected float strokeRadius;
		protected final float padRadius;

		public RectangleDrawable(Bitmap bitmap, Integer strokeColor, float strokeWidth,float padRadius) {
			radius = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2;

			bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mBitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setShader(bitmapShader);
			paint.setFilterBitmap(true);
			paint.setDither(true);

			if (strokeColor == null) {
				strokePaint = null;
			} else {
				strokePaint = new Paint();
				strokePaint.setStyle(Paint.Style.STROKE);
				strokePaint.setColor(strokeColor);
				strokePaint.setStrokeWidth(strokeWidth);
				strokePaint.setAntiAlias(true);
			}
			this.strokeWidth = strokeWidth;
			strokeRadius = radius - strokeWidth / 2;

			mRoundRect = new RectF(0, 0, strokeRadius * 2, strokeRadius * 2);
			mStrokeRoundRect = new RectF(0,0,radius * 2, radius * 2);
			this.padRadius = padRadius;
			L.i("RectangleBitmapDisplayer","RectangleDrawable --  mRoundRect="+mRoundRect+"--mStrokeRoundRect=" + mStrokeRoundRect);
		}

		@Override
		protected void onBoundsChange(Rect bounds) {
			super.onBoundsChange(bounds);

			mRect.set(0, 0, bounds.width(), bounds.height());
			radius = Math.min(bounds.width(), bounds.height()) / 2;
			strokeRadius = radius - strokeWidth / 2;
			mRoundRect = new RectF(0, 0, strokeRadius * 2, strokeRadius * 2);
			mStrokeRoundRect = new RectF(0,0,radius * 2, radius * 2);
			L.i("RectangleBitmapDisplayer","onBoundsChange -- mRoundRect="+mRoundRect+"--mStrokeRoundRect=" + mStrokeRoundRect);
			// Resize the original bitmap to fit the new bound
			Matrix shaderMatrix = new Matrix();
			shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
			bitmapShader.setLocalMatrix(shaderMatrix);
		}

		@Override
		public void draw(Canvas canvas) {
			L.i("RectangleBitmapDisplayer","draw");
			canvas.drawRoundRect(mRoundRect, padRadius, padRadius, paint);
			if (strokePaint != null) {
				canvas.drawRoundRect(mStrokeRoundRect, padRadius, padRadius, strokePaint);
			}
		}

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		@Override
		public void setAlpha(int alpha) {
			paint.setAlpha(alpha);
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
			paint.setColorFilter(cf);
		}
	}
}
