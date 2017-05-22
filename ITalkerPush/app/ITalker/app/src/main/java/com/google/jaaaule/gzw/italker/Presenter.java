package com.google.jaaaule.gzw.italker;

import android.text.TextUtils;

/**
 * Created by admin on 2017/5/22.
 */

public class Presenter implements IPresenter {
    private IView mView;

    public Presenter(IView view) {
        mView = view;
    }

    @Override
    public void submit() {
        //  开启 Loading
        String input = mView.getInputString();
        if (TextUtils.isEmpty(input)) {
            return;
        }

        IUser user = new UserQuery();

        String result = "RESULT: " + input + " - " + user.query(input.hashCode());
        mView.setResult(result);
        //  关闭 Loading
    }
}
