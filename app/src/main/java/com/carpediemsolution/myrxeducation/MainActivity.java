package com.carpediemsolution.myrxeducation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private PaginationRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private Subscription pagingSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(MainActivity.this, 3);
        //
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        // инициализируем adapter в первый раз
            recyclerViewAdapter = new PaginationRecyclerViewAdapter(MainActivity.this);
            recyclerViewAdapter.setHasStableIds(true);
        //
        recyclerView.setSaveEnabled(true);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        // если все items подгружены, Pagination не нужна
        if (recyclerViewAdapter.isAllItemsLoaded()) {
            return;
        }


        PaginationTool<List<Card>> paginationTool = PaginationTool.buildPagingObservable(recyclerView,
                offset -> EmulateResponseManager.getInstance().getserverResponse())
               // .setLimit(LIMIT)
                .build();

        pagingSubscription = paginationTool
                .getPagingObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Card>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Card> items) {
                        recyclerViewAdapter.addNewItems(items);
                        recyclerViewAdapter.notifyItemInserted(recyclerViewAdapter.getItemCount()
                                - items.size());
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (pagingSubscription != null && !pagingSubscription.isUnsubscribed()) {
            pagingSubscription.unsubscribe();
        }
        // for memory leak prevention (RecycleView is not unsubscibed from adapter DataObserver)
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
        super.onDestroy();
    }
}
