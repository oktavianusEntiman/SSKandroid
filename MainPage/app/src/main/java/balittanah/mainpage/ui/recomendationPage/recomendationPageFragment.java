package balittanah.mainpage.ui.recomendationPage;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import balittanah.mainpage.Myobj;
import balittanah.mainpage.R;
import balittanah.mainpage.ui.scanPage.scanPageFragment;

public class recomendationPageFragment extends Fragment {
    private Myobj myobj = new Myobj();
    Button action;
    TextView value;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recomendation_page_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        value = view.findViewById(R.id.value);
        action = view.findViewById(R.id.btnAction);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double v1 = 0.12312;
                value.setText(String.valueOf(myobj.getElementValue21()));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}