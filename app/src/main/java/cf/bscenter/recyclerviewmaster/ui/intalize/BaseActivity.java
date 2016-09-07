package cf.bscenter.recyclerviewmaster.ui.intalize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nghianv on 05/08/2016
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayout();

    protected abstract void main();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        main();
    }
}
