package org.reassembler.shackleford.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.reassembler.syn.model.SynListAdapter;

public class CardAdaptor extends SynListAdapter<Card> {
    public CardAdaptor(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CardView row = (CardView) view;

        Card card = (Card) getItem(i);
        if (row == null) {
            row = new CardView(this.context);

            TextView tv = new TextView(this.context);
            ImageView iv = new ImageView(this.context);

            row.addView(tv);
            row.addView(iv);

            row.setTextView(tv);
            row.setImageView(iv);
        }


        row.getTextView().setText(card.toString());

        Bitmap bm = BitmapFactory.decodeResource(this.context.getResources(), card.getResourceId());

        row.getImageView().setScaleType(ImageView.ScaleType.FIT_END);
        row.getImageView().setImageBitmap(bm);
        row.getImageView().setAdjustViewBounds(true);

        return row;
    }
}
