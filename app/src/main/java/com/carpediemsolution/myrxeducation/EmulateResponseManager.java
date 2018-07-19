package com.carpediemsolution.myrxeducation;

import android.util.Log;
import java.util.List;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Юлия on 19.06.2017.
 */

class EmulateResponseManager {

    private static final String LOG_TAG = EmulateResponseManager.class.getSimpleName();

    private static volatile EmulateResponseManager client;

//инициализация объекта EmulateResponseManager
    public static EmulateResponseManager getInstance() {
        if (client == null) {
            synchronized (EmulateResponseManager.class) {
                if (client == null) {
                    client = new EmulateResponseManager();
                }
            }
        }
        return client;
    }

    public Observable<List<Card>> getObserverResponse() {
            Observable<List<Card>> observable = App.getWebApi().getCards();

            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    //подписываем Observer, к. обрабатывает пакет карточек с сервера
                    .subscribe(new Observer<List<Card>>() {
                        @Override
                        public void onCompleted() {
                            Log.d(LOG_TAG,"onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(LOG_TAG,"onError " + e);
                        }

                        @Override
                        public void onNext(List<Card> cards) {
                            Log.d(LOG_TAG, "onNext " + cards.size());
                        }
                    });
        //возвращаем
            return observable;
        }
}
