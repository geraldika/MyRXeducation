package com.carpediemsolution.myrxeducation.api;

import com.carpediemsolution.myrxeducation.Card;

import java.util.List;
import retrofit2.http.GET;
import rx.Observable;


/**
 * Created by Юлия on 27.03.2017.
 */

public interface API {

    @GET("/languageapp/cards/all")
    Observable<List<Card>> getCards();
}
