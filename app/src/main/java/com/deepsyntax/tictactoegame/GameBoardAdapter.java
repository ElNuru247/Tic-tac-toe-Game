package com.deepsyntax.tictactoegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameBoardAdapter extends ArrayAdapter {
    private int[] mWinPattern;
    private int[] mPlayedMoves;
    private Board mBoard;
    private Context mContext;
    private Themes themes;
    private static int themeIndex = 0;
    private boolean redisplay;

    public GameBoardAdapter(Context ctx, ArrayList boxes) {
        super(ctx, 0, boxes);
        mContext = ctx;
        mBoard = new Board(ctx);
        mBoard.setNumsBoxes(boxes);
        themes = new Themes(ctx);
    }

    public GameBoardAdapter(Context ctx, ArrayList boxes, int symbol, int[] winPattern, int[] playedMoves) {
        super(ctx, 0, boxes);
        mContext = ctx;
        mWinPattern = winPattern;
        mPlayedMoves = playedMoves;
        mBoard = new Board(ctx, symbol, winPattern, playedMoves);
        mBoard.setNumsBoxes(boxes);
        themes = new Themes(ctx);
    }

    public GameBoardAdapter(Context ctx, ArrayList boxes, int symbol, int[] playedMoves) {
        super(ctx, 0, boxes);
        mContext = ctx;
        mPlayedMoves = playedMoves;
        mBoard = new Board(ctx, symbol, playedMoves);
        mBoard.setNumsBoxes(boxes);
        themes = new Themes(ctx);
    }

    @NonNull
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_box, parent, false);
        ImageView box = convertView.findViewById(R.id.game_box);
        themes.setNewTheme((FrameLayout) convertView, box, themeIndex);
        if (mWinPattern != null) {
            mBoard.displayPattern(convertView, position);
        } else if (mPlayedMoves != null ||  redisplay) {
            mBoard.reDisplayBoard(convertView, position);
        }

        mBoard.setBox(box);
        box.getLayoutParams().height = mBoard.pixelsToDips(Board.getBoxHeight());
        box.getLayoutParams().width = mBoard.pixelsToDips(Board.getBoxWidth());
        mBoard.drawBoardLines(convertView, position);

        return convertView;
    }

    public void setThemeIndex(int index) {
        themeIndex = index;
    }
    public  void redisplay(boolean state){
        redisplay=state;
    }
}
