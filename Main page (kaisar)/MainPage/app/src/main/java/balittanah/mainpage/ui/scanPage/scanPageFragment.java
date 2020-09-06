package balittanah.mainpage.ui.scanPage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import balittanah.mainpage.R;
import balittanah.mainpage.ui.dataPage.dataPageFragment;

public class scanPageFragment extends Fragment {

    public LineChart lineChart;
    CardView btnRefresh, btnBackground, btnScan, btnProcess;
    Spinner edtResolution, edtOptical;

    String[] Resolution = {"1px", "2px", "3px", "4px"};
    String[] Optical = {"1px", "2px", "3px", "4px"};

    RequestQueue mQueue;

    public double x;
    public double y;

    public ArrayList<Entry> allAxis;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scan_page, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> itemResolution = new ArrayAdapter<String>
                (getActivity(), R.layout.support_simple_spinner_dropdown_item, Resolution);
        ArrayAdapter<String> itemOptical = new ArrayAdapter<String>
                (getActivity(), R.layout.support_simple_spinner_dropdown_item, Optical);

        mQueue = Volley.newRequestQueue(getActivity());

        lineChart = view.findViewById(R.id.graph);
        btnRefresh = view.findViewById(R.id.btnRefresh);
        btnBackground = view.findViewById(R.id.btnBackground);
        btnScan = view.findViewById(R.id.btnScan);
        btnProcess = view.findViewById(R.id.btnProcess);
        edtResolution = view.findViewById(R.id.edtResolution);
        edtOptical = view.findViewById(R.id.edtOptical);

        edtResolution.setAdapter(itemResolution);
        edtOptical.setAdapter(itemOptical);

        allAxis = new ArrayList<Entry>();

        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        XAxis xl = lineChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend l = lineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fungsi refresh
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

                bottomSheetDialog.setCanceledOnTouchOutside(false);

                LinearLayout bottom_sheet_refresh = bottomSheetDialog.findViewById(R.id.bottom_sheet_refresh);
                CardView btn_yes = bottomSheetDialog.findViewById(R.id.btn_yes);
                CardView btn_cancel = bottomSheetDialog.findViewById(R.id.btn_cancel);
                bottom_sheet_refresh.setVisibility(View.VISIBLE);

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fungsi yes pada bottom sheet
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fungsi cancel bottom sheet
                        bottomSheetDialog.hide();
                    }
                });
                bottomSheetDialog.show();
            }
        });

        btnBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fungsi scan background
                Toast.makeText(getActivity(), "button background", Toast.LENGTH_SHORT).show();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fungsi scan
                ParsingJson();
            }
        });

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fungsi process
                final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(getActivity());
                bottomSheetDialog2.setContentView(R.layout.bottom_sheet_dialog);

                bottomSheetDialog2.setCanceledOnTouchOutside(false);

                LinearLayout bottom_sheet_process = bottomSheetDialog2.findViewById(R.id.bottom_sheet_process);
                CardView btn_yes2 = bottomSheetDialog2.findViewById(R.id.btn_yes2);
                CardView btn_cancel2 = bottomSheetDialog2.findViewById(R.id.btn_cancel2);
                bottom_sheet_process.setVisibility(View.VISIBLE);

                btn_yes2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fungsi yes pada bottom sheet
                        FrameLayout listFragment;
                        listFragment = view.findViewById(R.id.nav_host_fragment);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        dataPageFragment mdataPageFragment = new dataPageFragment();
                        fragmentTransaction.replace(R.id.nav_host_fragment, mdataPageFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        bottomSheetDialog2.hide();
                    }
                });

                btn_cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fungsi cancel bottom sheet
                        bottomSheetDialog2.hide();
                    }
                });
                bottomSheetDialog2.show();
            }
        });
    }

    public void ParsingJson() {
        String BASE_URL = "http://169.254.104.193/ssk/index.php"; //dari local host
        String URL = "https://graphdataandro.000webhostapp.com/"; //dari hosting

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getActivity(), "show a graph from json", Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = response.getJSONArray("absorbance");
                            JSONArray jsonArray2 = response.getJSONArray("wavenumber");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                y = jsonArray.getDouble(i);
                                x = jsonArray2.getDouble(i);

                                Log.d("absorbance :", String.valueOf(y));

                                Log.d("wavenumber :", String.valueOf(x));

                                allAxis.add(new Entry((float) x, (float) y));
                                LineDataSet dataSet = new LineDataSet(allAxis, "Absorbance v/s wavelength graph");
                                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(dataSet);
                                LineData data = new LineData(dataSets);
                                lineChart.setData(data);
                                lineChart.animateY(5000);
                                lineChart.getDescription().setEnabled(false);
                                lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);  //curve line
                                lineChart.invalidate();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(jsonObjectRequest);
    }
}