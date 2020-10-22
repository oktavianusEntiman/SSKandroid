package balittanah.mainpage.ui.scanPage;

import android.content.Entity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import balittanah.mainpage.Myobj;
import balittanah.mainpage.R;
import balittanah.mainpage.ui.dataPage.dataPageFragment;

public class scanPageFragment extends Fragment {

    public LineChart lineChart;
    CardView btnRefresh, btnBackground, btnScan, btnProcess;
    Spinner edtResolution, edtOptical;
    TextView tampung;
    public Myobj datareflect = new Myobj();

    String[] Resolution = {"1px", "2px", "3px", "4px"};
    String[] Optical = {"1px", "2px", "3px", "4px"};

    RequestQueue mQueue;
    LinearLayout lLoading;
    RelativeLayout lUtama;

    public boolean loading = false;

    public double x;
    public double y;
    public String reflectance;

    public ArrayList<Entry> allAxis;

    public ProgressBar progressBar;

    public float a[] = {(float) 3.8704317, (float) 3.481731, (float) 3.112639, (float) 2.7631557, (float) 2.4332812, (float) 2.1230156, (float) 1.8218051, (float) 1.5428181, (float) 1.2832037, (float) 1.0538046, (float) 0.85943544, (float) 0.68840075, (float) 0.5289379, (float) 0.38665625, (float) 0.27332777, (float) 0.18161932, (float) 0.08654734, (float) -0.0206862, (float) -0.108966105, (float) -0.12719381, (float) -0.0497196, (float) 0.101320304, (float) 0.27595317, (float) 0.42437986, (float) 0.5014028, (float) 0.46307, (float) 0.28456038, (float) -0.010958054, (float) -0.35087943, (float) -0.6505429, (float) -0.8579766, (float) -0.966181, (float) -0.99124056, (float) -0.95187587, (float) -0.86913055, (float) -0.76952547, (float) -0.67543626, (float) -0.5922723, (float) -0.5098972, (float) -0.41574466, (float) -0.3033343, (float) -0.1700764, (float) -0.013439285, (float) 0.16726658, (float) 0.36836216, (float) 0.58351094, (float) 0.8051464, (float) 1.0229396, (float) 1.2285992, (float) 1.4324409, (float) 1.6730129, (float) 1.9892191, (float) 2.3624659, (float) 2.6959639, (float) 2.8669596, (float) 2.7966132, (float) 2.477003, (float) 1.9541258, (float) 1.3045062, (float) 0.6307705, (float) 0.05442586, (float) -0.32863703, (float) -0.5063816, (float) -0.5508209, (float) -0.5508058, (float) -0.55125475, (float) -0.54923165, (float) -0.5316622, (float) -0.5056818, (float) -0.4905323, (float) -0.4927884, (float) -0.5026369, (float) -0.511617, (float) -0.5226538, (float) -0.5397273, (float) -0.557486, (float) -0.5681975, (float) -0.5737165, (float) -0.5825461, (float) -0.5962707, (float) -0.60664606, (float) -0.6077426, (float) -0.6040681, (float) -0.6027949, (float) -0.6036157, (float) -0.60227346, (float) -0.6007115, (float) -0.60546523, (float) -0.6151927, (float) -0.6173562, (float) -0.6018677, (float) -0.5740234, (float) -0.54852605, (float) -0.53334844, (float) -0.52554464, (float) -0.521515, (float) -0.52359456, (float) -0.5329663, (float) -0.5419728, (float) -0.5402035, (float) -0.5268787, (float) -0.51201147, (float) -0.5050308, (float) -0.5069488, (float) -0.5145626, (float) -0.5267139, (float) -0.542692, (float) -0.5577988, (float) -0.56623465, (float) -0.56824076, (float) -0.5693699, (float) -0.57191694, (float) -0.57105494, (float) -0.5614464, (float) -0.5438553, (float) -0.52165323, (float) -0.49445596, (float) -0.46071154, (float) -0.42623934, (float) -0.4034071, (float) -0.39805487, (float) -0.40123755, (float) -0.3976369, (float) -0.3812221, (float) -0.35917893, (float) -0.34149748, (float) -0.33199087, (float) -0.32950383, (float) -0.3319943, (float) -0.33497623, (float) -0.32861322, (float) -0.30119225, (float) -0.24604103, (float) -0.16432618, (float) -0.06356148, (float) 0.042029366, (float) 0.13077156, (float) 0.18077044, (float) 0.18170333, (float) 0.13687417, (float) 0.052706983, (float) -0.06808441, (float) -0.21786228, (float) -0.37439826, (float) -0.51085794, (float) -0.61846566, (float) -0.71201676, (float) -0.8074793, (float) -0.90100205, (float) -0.97350466, (float) -1.0353855, (float) -1.0866444, (float) -1.1272817, (float) -1.157297};

    public double b[];

    public float c[];
    public ArrayList<Float> allc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scan_page, container, false);
        return root;
    }

    String URL = "https://sskapi.azurewebsites.net/api/Inference/ProcessData";


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String data = "{\"Reflectance\":[3.8704317,3.481731,3.112639,2.7631557,2.4332812,2.1230156,1.8218051,1.5428181,1.2832037,1.0538046,0.85943544,0.68840075,0.5289379,0.38665625,0.27332777,0.18161932,0.08654734,-0.0206862,-0.108966105,-0.12719381,-0.0497196,0.101320304,0.27595317,0.42437986,0.5014028,0.46307,0.28456038,-0.010958054,-0.35087943,-0.6505429,-0.8579766,-0.966181,-0.99124056,-0.95187587,-0.86913055,-0.76952547,-0.67543626,-0.5922723,-0.5098972,-0.41574466,-0.3033343,-0.1700764,-0.013439285,0.16726658,0.36836216,0.58351094,0.8051464,1.0229396,1.2285992,1.4324409,1.6730129,1.9892191,2.3624659,2.6959639,2.8669596,2.7966132,2.477003,1.9541258,1.3045062,0.6307705,0.05442586,-0.32863703,-0.5063816,-0.5508209,-0.5508058,-0.55125475,-0.54923165,-0.5316622,-0.5056818,-0.4905323,-0.4927884,-0.5026369,-0.511617,-0.5226538,-0.5397273,-0.557486,-0.5681975,-0.5737165,-0.5825461,-0.5962707,-0.60664606,-0.6077426,-0.6040681,-0.6027949,-0.6036157,-0.60227346,-0.6007115,-0.60546523,-0.6151927,-0.6173562,-0.6018677,-0.5740234,-0.54852605,-0.53334844,-0.52554464,-0.521515,-0.52359456,-0.5329663,-0.5419728,-0.5402035,-0.5268787,-0.51201147,-0.5050308,-0.5069488,-0.5145626,-0.5267139,-0.542692,-0.5577988,-0.56623465,-0.56824076,-0.5693699,-0.57191694,-0.57105494,-0.5614464,-0.5438553,-0.52165323,-0.49445596,-0.46071154,-0.42623934,-0.4034071,-0.39805487,-0.40123755,-0.3976369,-0.3812221,-0.35917893,-0.34149748,-0.33199087,-0.32950383,-0.3319943,-0.33497623,-0.32861322,-0.30119225,-0.24604103,-0.16432618,-0.06356148,0.042029366,0.13077156,0.18077044,0.18170333,0.13687417,0.052706983,-0.06808441,-0.21786228,-0.37439826,-0.51085794,-0.61846566,-0.71201676,-0.8074793,-0.90100205,-0.97350466,-1.0353855,-1.0866444,-1.1272817,-1.157297]}";

        datareflect.setDataReflectance(data);

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
        progressBar = view.findViewById(R.id.spin_kit);
        tampung = view.findViewById(R.id.tampung);
        lUtama = view.findViewById(R.id.lUtama);
        lLoading = view.findViewById(R.id.lLoading);

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


        progressBar.setVisibility(View.GONE);


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
                        mQueue.getCache().clear();
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
//                Toast.makeText(getActivity(), "value 20 = " + datareflect.getElementValue21(), Toast.LENGTH_LONG).show();
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

                        send();
//                        String isi = "3.8704317,3.481731,3.112639,2.7631557,2.4332812,2.1230156,1.8218051,1.5428181,1.2832037,1.0538046,0.85943544,0.68840075,0.5289379,0.38665625,0.27332777,0.18161932,0.08654734,-0.0206862,-0.108966105,-0.12719381,-0.0497196,0.101320304,0.27595317,0.42437986,0.5014028,0.46307,0.28456038,-0.010958054,-0.35087943,-0.6505429,-0.8579766,-0.966181,-0.99124056,-0.95187587,-0.86913055,-0.76952547,-0.67543626,-0.5922723,-0.5098972,-0.41574466,-0.3033343,-0.1700764,-0.013439285,0.16726658,0.36836216,0.58351094,0.8051464,1.0229396,1.2285992,1.4324409,1.6730129,1.9892191,2.3624659,2.6959639,2.8669596,2.7966132,2.477003,1.9541258,1.3045062,0.6307705,0.05442586,-0.32863703,-0.5063816,-0.5508209,-0.5508058,-0.55125475,-0.54923165,-0.5316622,-0.5056818,-0.4905323,-0.4927884,-0.5026369,-0.511617,-0.5226538,-0.5397273,-0.557486,-0.5681975,-0.5737165,-0.5825461,-0.5962707,-0.60664606,-0.6077426,-0.6040681,-0.6027949,-0.6036157,-0.60227346,-0.6007115,-0.60546523,-0.6151927,-0.6173562,-0.6018677,-0.5740234,-0.54852605,-0.53334844,-0.52554464,-0.521515,-0.52359456,-0.5329663,-0.5419728,-0.5402035,-0.5268787,-0.51201147,-0.5050308,-0.5069488,-0.5145626,-0.5267139,-0.542692,-0.5577988,-0.56623465,-0.56824076,-0.5693699,-0.57191694,-0.57105494,-0.5614464,-0.5438553,-0.52165323,-0.49445596,-0.46071154,-0.42623934,-0.4034071,-0.39805487,-0.40123755,-0.3976369,-0.3812221,-0.35917893,-0.34149748,-0.33199087,-0.32950383,-0.3319943,-0.33497623,-0.32861322,-0.30119225,-0.24604103,-0.16432618,-0.06356148,0.042029366,0.13077156,0.18077044,0.18170333,0.13687417,0.052706983,-0.06808441,-0.21786228,-0.37439826,-0.51085794,-0.61846566,-0.71201676,-0.8074793,-0.90100205,-0.97350466,-1.0353855,-1.0866444,-1.1272817,-1.157297";
//
//                        String data = "{\"Reflectance\":[";
//                        data += isi;
////                        for (int i = 0; i < b.length; i++){
////                            if (i != 0) // if it isn't the first one
////                                data += ", "; // insert a separator
////                            else
////                                data += a[i]; // append the index's toString
////                        }
//                        data += "]}";
//                        send(data);
//                        FrameLayout listFragment;
//                        listFragment = view.findViewById(R.id.nav_host_fragment);
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        dataPageFragment mdataPageFragment = new dataPageFragment();
//                        fragmentTransaction.replace(R.id.nav_host_fragment, mdataPageFragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
                        bottomSheetDialog2.hide();
                    }
                });

                btn_cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fungsi cancel bottom sheet
                        progressBar.setVisibility(View.GONE);
                        bottomSheetDialog2.hide();
                    }
                });
                bottomSheetDialog2.show();
            }
        });
    }

    public void ParsingJson() {
        loading = true;
        if (loading) {
            Wave rotatingCircle = new Wave();
            progressBar.setVisibility(View.VISIBLE);
            lLoading.setVisibility(View.VISIBLE);
            lUtama.setVisibility(View.GONE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setIndeterminateDrawable(rotatingCircle);
        }


        String BASE_URL = "http://192.168.43.125/ssk/index.php"; //dari local host
        String URL2 = "https://graphdataandro.000webhostapp.com/"; //dari hosting

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("absorbance");
                            String arrayR = "{\"Reflectance\":" + jsonArray + "}";
//                            Toast.makeText(getActivity(), arrayR, Toast.LENGTH_LONG).show();
//
//                            datareflect.dataReflectance = arrayR;
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


                                btnBackground.setEnabled(false);
                                btnBackground.setCardBackgroundColor(Color.parseColor("#FF0000"));
                                btnScan.setEnabled(false);
                                btnScan.setCardBackgroundColor(Color.parseColor("#FF0000"));
                                btnProcess.setCardBackgroundColor(Color.parseColor("#3B7FF4"));
                                progressBar.setVisibility(View.GONE);
                                lUtama.setVisibility(View.VISIBLE);
                                lLoading.setVisibility(View.GONE);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            lUtama.setVisibility(View.VISIBLE);
                            lLoading.setVisibility(View.GONE);
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.GONE);
                lUtama.setVisibility(View.VISIBLE);
                lLoading.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    public void send() {
        Wave rotatingCircle = new Wave();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminateDrawable(rotatingCircle);
        lUtama.setVisibility(View.GONE);
        lLoading.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final String reflect = datareflect.getDataReflectance();
        String URL = "https://sskapi.azurewebsites.net/api/Inference/ProcessData";
        mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.e("jumlah array:", String.valueOf(jsonArray.length()));

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String elementName = jsonObject1.getString("elementName");
                                double elementValue = jsonObject1.getDouble("elementValue");

                                if (i==0) {
                                    datareflect.setElementValue1(elementValue);
                                }
                                if (i==1) {
                                    datareflect.setElementValue2(elementValue);
                                }
                                if (i==2) {
                                    datareflect.setElementValue3(elementValue);
                                }
                                if (i==3) {
                                    datareflect.setElementValue4(elementValue);
                                }
                                if (i==4) {
                                    datareflect.setElementValue5(elementValue);
                                }
                                if (i==5) {
                                    datareflect.setElementValue6(elementValue);
                                }
                                if (i==6) {
                                    datareflect.setElementValue7(elementValue);
                                }
                                if (i==7) {
                                    datareflect.setElementValue8(elementValue);
                                }
                                if (i==8) {
                                    datareflect.setElementValue9(elementValue);
                                }
                                if (i==9) {
                                    datareflect.setElementValue10(elementValue);
                                }
                                if (i==10) {
                                    datareflect.setElementValue11(elementValue);
                                }
                                if (i==11) {
                                    datareflect.setElementValue12(elementValue);
                                }
                                if (i==12) {
                                    datareflect.setElementValue13(elementValue);
                                }
                                if (i==13) {
                                    datareflect.setElementValue14(elementValue);
                                }
                                if (i==14) {
                                    datareflect.setElementValue15(elementValue);
                                }
                                if (i==15) {
                                    datareflect.setElementValue16(elementValue);
                                }
                                if (i==16) {
                                    datareflect.setElementValue17(elementValue);
                                }
                                if (i==17) {
                                    datareflect.setElementValue18(elementValue);
                                }
                                if (i==18) {
                                    datareflect.setElementValue19(elementValue);
                                }
                                if (i==19) {
                                    datareflect.setElementValue20(elementValue);
                                }
                                if (i==20) {
                                    datareflect.setElementValue21(elementValue);
                                }

//                                Toast.makeText(getActivity(), "RESPONSE:" + elementName + " = " + elementValue, Toast.LENGTH_SHORT).show();
                                Log.e("response:", String.valueOf(elementName + " = " + elementValue));
                            }
                            lUtama.setVisibility(View.VISIBLE);
                            lLoading.setVisibility(View.GONE);
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return reflect == null ? null : reflect.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        mQueue.add(stringRequest);
    }
}