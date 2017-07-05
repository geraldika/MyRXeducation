package com.carpediemsolution.myrxeducation;

import rx.Observable;

/**
 * Created by Юлия on 19.06.2017.
 */

public interface PagingListener<T> {
    Observable<T> onNextPage(int offset);
}