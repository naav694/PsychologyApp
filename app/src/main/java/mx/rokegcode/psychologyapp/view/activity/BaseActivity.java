package mx.rokegcode.psychologyapp.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.rokegcode.psychologyapp.R;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    protected abstract int getLayoutResource();

    protected void onDestroy() {
        super.onDestroy();
        /*if (mPresenter != null) {
            mPresenter.onDispose();
        }*/
    }

}
