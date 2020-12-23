package com.si_ware.neospectra.Activities.Interfaces.RecomendationPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.balittanah.gravicode.pkdss.FertilizerCalculator;
import com.balittanah.gravicode.pkdss.FertilizerInfo;
import com.si_ware.neospectra.Activities.Interfaces.ScanPage.ScanPageFragment;
import com.si_ware.neospectra.Activities.IntroActivity;
import com.si_ware.neospectra.Activities.SettingsActivity;
import com.si_ware.neospectra.BluetoothSDK.SWS_P3API;
import com.si_ware.neospectra.DataElements;
import com.si_ware.neospectra.R;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

import static com.si_ware.neospectra.Global.GlobalVariables.bluetoothAPI;

public class RecomendationPageFragment extends Fragment {
    private Context mContext;
    CardView sync;
    TextView txUrea, txSp36, txKcl, txNpk, txUrea15, lblUrea15;

    Spinner cmbKomoditas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recomendation_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //comment or uncomment this function to test the aplication without connect to device
//        if (bluetoothAPI == null) {
//            bluetoothAPI = new SWS_P3API(getActivity(), mContext);
//        }
        mContext = getActivity();

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left);

        Toolbar toolbar = view.findViewById(R.id.titlebar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Rekomendasi Pemupukan");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        sync = view.findViewById(R.id.sync);
        txUrea = view.findViewById(R.id.txtUrea);
        txSp36 = view.findViewById(R.id.txtSp35);
        txKcl = view.findViewById(R.id.txtKcl);
        txNpk = view.findViewById(R.id.txtNpk);
        txUrea15 = view.findViewById(R.id.txtUrea15);
        lblUrea15 = view.findViewById(R.id.lblUrea15);

        cmbKomoditas = view.findViewById(R.id.aSpinner);

        txUrea.setText(DataElements.getUrea());
        txSp36.setText(DataElements.getSp36());
        txKcl.setText(DataElements.getKcl());
        txNpk.setText(DataElements.getNpk());
        txUrea15.setText(DataElements.getUrea15());

        String compareValue = DataElements.getKomoditas();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.Spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbKomoditas.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            cmbKomoditas.setSelection(spinnerPosition);
        }
        cmbKomoditas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] items = getResources().getStringArray(R.array.Spinner_items);
                DataElements.setKomoditas(items[position]);
                Calculator();
                SetUI();
                Toast.makeText(mContext, "Komoditas " + items[position], Toast.LENGTH_SHORT).show();
                /*ScanPageFragment obj = new ScanPageFragment();
                obj.Calculator();
                if(items[position]=="Kedelai"){
                    lblUrea15.setVisibility(View.INVISIBLE);
                    txUrea15.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, "Pilihan komoditas saat ini adalah Kedelai", Toast.LENGTH_LONG).show();
                }
                else{
                    lblUrea15.setVisibility(View.VISIBLE);
                    txUrea15.setVisibility(View.VISIBLE);

                    Toast.makeText(mContext, "Pilihan komoditas saat ini adalah " + items[position], Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        sync.setOnClickListener(view13 -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

            bottomSheetDialog.setCanceledOnTouchOutside(false);

            LinearLayout bottom_sheet_refresh = bottomSheetDialog.findViewById(R.id.bottom_sheet_sync);
            CardView btn_yes = bottomSheetDialog.findViewById(R.id.buttonYes);
            CardView btn_cancel = bottomSheetDialog.findViewById(R.id.buttonNo);
            bottom_sheet_refresh.setVisibility(View.VISIBLE);

            btn_yes.setOnClickListener(view1 -> {
                Toast.makeText(getActivity(), "Berhasil Sync Data..", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            });

            btn_cancel.setOnClickListener(view12 -> {
                Toast.makeText(getActivity(), "cancel..", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            });

            bottomSheetDialog.show();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            android.support.v7.app.AlertDialog.Builder myAlert = new android.support.v7.app.AlertDialog
                    .Builder(mContext);
            myAlert.setTitle("Disconnect");
            myAlert.setMessage("Are you sure you want to disconnect the device?");
            myAlert.setPositiveButton("OK", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                // Request of bluetooth disconnection
                if (bluetoothAPI != null) {
                    bluetoothAPI.disconnectFromDevice();

                    Intent iMain = new Intent(mContext, IntroActivity.class);
                    iMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(iMain);
                }

            });
            myAlert.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            myAlert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Calculator() {
        Ini ini = null;
        try {
            InputStream inputStream = getContext().getAssets().open("config.ini");
            ini = new Ini(inputStream);
            java.util.prefs.Preferences prefs = new IniPreferences(ini);
            //System.out.println("grumpy/homePage: " + prefs.node("grumpy").get("homePage", null));

            String DataRekomendasi = ini.get("Config", "DataRekomendasi");
            try {
                double ureaConst = Double.parseDouble(ini.get("Config", "Urea"));
                double sp36Const = Double.parseDouble(ini.get("Config", "SP36"));
                double kclConst = Double.parseDouble(ini.get("Config", "KCL"));

                double KCLMin = Double.parseDouble(ini.get("Config", "KCLMin"));
                double UreaMin = Double.parseDouble(ini.get("Config", "UreaMin"));
                double SP36Min = Double.parseDouble(ini.get("Config", "SP36Min"));

                FertilizerCalculator calc = new FertilizerCalculator(getContext());
                double ureaVal = 0;
                double sp36Val = 0;
                double kclVal = 0;
                switch (DataElements.getKomoditas()) {
                    case "Padi":
                        ureaVal = calc.GetFertilizerDoze(DataElements.getKjeldahlN(), DataElements.getKomoditas(), "Urea") * ureaConst;
                        sp36Val = calc.GetFertilizerDoze(DataElements.getHCl25P2O5(), DataElements.getKomoditas(), "SP36") * sp36Const;
                        kclVal = calc.GetFertilizerDoze(DataElements.getHCl25K2O(), DataElements.getKomoditas(), "KCL") * kclConst;
                        break;
                    case "Jagung":
                        ureaVal = calc.GetFertilizerDoze(DataElements.getKjeldahlN(), DataElements.getKomoditas(), "Urea") * ureaConst;
                        sp36Val = calc.GetFertilizerDoze(DataElements.getBray1P2O5(), DataElements.getKomoditas(), "SP36") * sp36Const;
                        kclVal = calc.GetFertilizerDoze(DataElements.getHCl25K2O(), DataElements.getKomoditas(), "KCL") * kclConst;
                        break;
                    case "Kedelai":
                        ureaVal = calc.GetFertilizerDoze(DataElements.getKjeldahlN(), DataElements.getKomoditas(), "Urea") * ureaConst;
                        sp36Val = calc.GetFertilizerDoze(DataElements.getBray1P2O5(), DataElements.getKomoditas(), "SP36") * sp36Const;
                        kclVal = calc.GetFertilizerDoze(DataElements.getK(), DataElements.getKomoditas(), "KCL") * kclConst;
                        break;

                }
                ureaVal = ureaVal < UreaMin ? UreaMin : ureaVal;
                sp36Val = sp36Val < SP36Min ? SP36Min : sp36Val;
                kclVal = kclVal < KCLMin ? KCLMin : kclVal;

                String TxtUrea = String.valueOf(ureaVal);
                String TxtSP36 = String.valueOf(sp36Val);
                String TxtKCL = String.valueOf(kclVal);
                System.out.println(String.format("Rekomendasi KCL : %1$s, SP36 : %2$s, Urea : %3$s", TxtKCL, TxtSP36, TxtUrea));

                FertilizerInfo x = calc.GetNPKDoze(DataElements.getHCl25P2O5(), DataElements.getHCl25K2O(), DataElements.getKomoditas());
                String Urea = String.valueOf(x.getUrea());
                String Npk = String.valueOf(x.getNPK());


                System.out.println(String.format("Rekomendasi NPK 15:15:15 = %1$s", Npk));
                System.out.println(String.format("UREA 15:15:15 = %1$s", Urea));

                DataElements.setUrea(TxtUrea);
                DataElements.setSp36(TxtSP36);
                DataElements.setKcl(TxtKCL);
                DataElements.setNpk(Npk);
                DataElements.setUrea15(Urea);

            } catch (RuntimeException ex) {
                System.out.println(ex);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void SetUI()
    {
        txUrea.setText(DataElements.getUrea());
        txSp36.setText(DataElements.getSp36());
        txKcl.setText(DataElements.getKcl());
        txNpk.setText(DataElements.getNpk());
        txUrea15.setText(DataElements.getUrea15());
    }

    @Override
    public void onResume() {
        //comment or uncomment this function to test the aplication without connect to device
//        if (bluetoothAPI != null) {
//            if (!bluetoothAPI.isDeviceConnected()) {
//                endActivity();
//                return;
//            }
//        }
        super.onResume();
    }

    private void endActivity() {
        bluetoothAPI = null;
        Intent mIntent = new Intent(getActivity(), IntroActivity.class);
        startActivity(mIntent);
    }
}