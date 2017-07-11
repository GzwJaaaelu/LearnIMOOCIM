package com.google.jaaaule.gzw.factory.data.helper;

import com.google.jaaaule.gzw.factory.Factory;
import com.google.jaaaule.gzw.factory.data.DataSource;
import com.google.jaaaule.gzw.factory.model.api.RspModel;
import com.google.jaaaule.gzw.factory.model.api.user.UserUpdateModel;
import com.google.jaaaule.gzw.factory.model.card.UserCard;
import com.google.jaaaule.gzw.factory.model.db.User;
import com.google.jaaaule.gzw.factory.net.Network;
import com.google.jaaaule.gzw.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/7/11.
 */

public class UserHelper {

    public static void update(UserUpdateModel userUpdate, final DataSource.Callback<UserCard> callback) {
        RemoteService service = Network.getRemoteService();
        service.updateUserInfo(userUpdate).enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard card = rspModel.getResult();

                    //  持久化 UserCard 转为 User
                    User user = card.build();
                    user.save();

                    callback.onDataLoaded(card);
                } else {
                    Factory.decodeRepModel(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                t.printStackTrace();
                callback.onDataNotAvailable(com.google.jaaaule.gzw.lang.R.string.data_network_error);
            }
        });
    }
}
