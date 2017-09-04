package com.reposh.fmgurbanov.hiretest.eratosthenes;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Pair;
import com.reposh.fmgurbanov.hiretest.eratosthenes.baseComponents.ActivityViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

import static com.reposh.fmgurbanov.hiretest.eratosthenes.utils.FieldUtils.toObservable;

/**
 * Created by Fedor on 03.09.2017.
 */

public class MainViewModel extends ActivityViewModel<MainActivity> {
    public final ObservableField<String> inputNumber = new ObservableField<>();
    public final ObservableField<String> sum = new ObservableField<>();
    public final ObservableField<String> result = new ObservableField<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;

    MainViewModel(MainActivity activity) {
        super(activity);

        context = activity.getApplicationContext();

        ISieve sieve = new AktineSieve();

        Observable<String> inputStream = toObservable(inputNumber);

        Disposable disposable = inputStream.throttleLast(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .filter(string -> !string.isEmpty())
                .map(Long::parseLong)
                .filter(aLong -> aLong > 1)
                .map(sieve::getPrimesUpTo)
                .map(bitSet -> {
                            Long sum = 0L;
                            StringBuilder sb = new StringBuilder();
                            int bSize = bitSet.length();
                            for (int number = 2; number <= bSize; ++number)
                                if (bitSet.get(number)) {
                                    sum += number;
                                    sb.append(number)
                                            .append(", ");
                                }
                            int lastComma = sb.lastIndexOf(",");
                            if (lastComma > 0)
                                sb.deleteCharAt(lastComma);
                            return new Pair<>(sum, sb.toString());
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(pair -> {
                    String line = context.getResources().getString(R.string.Result_sum, pair.first);
                    result.set(pair.second);
                    this.sum.set(line);
                });

        compositeDisposable.add(disposable);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
