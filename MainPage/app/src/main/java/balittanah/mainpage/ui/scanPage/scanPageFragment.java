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

import balittanah.mainpage.R;
import balittanah.mainpage.ui.dataPage.dataPageFragment;

public class scanPageFragment extends Fragment {

    public LineChart lineChart;
    CardView btnRefresh, btnBackground, btnScan, btnProcess;
    Spinner edtResolution, edtOptical;
    TextView tampung;

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

    public float a[] = {(float) 3.8704317,(float) 3.481731,(float) 3.112639,(float) 2.7631557,(float) 2.4332812,(float) 2.1230156,(float) 1.8218051,(float) 1.5428181,(float) 1.2832037,(float) 1.0538046,(float) 0.85943544,(float) 0.68840075,(float) 0.5289379,(float) 0.38665625,(float) 0.27332777,(float) 0.18161932,(float) 0.08654734,(float) -0.0206862,(float) -0.108966105,(float) -0.12719381,(float) -0.0497196,(float) 0.101320304,(float) 0.27595317,(float) 0.42437986,(float) 0.5014028,(float) 0.46307,(float) 0.28456038,(float) -0.010958054,(float) -0.35087943,(float) -0.6505429,(float) -0.8579766,(float) -0.966181,(float) -0.99124056,(float) -0.95187587,(float) -0.86913055,(float) -0.76952547,(float) -0.67543626,(float) -0.5922723,(float) -0.5098972,(float) -0.41574466,(float) -0.3033343,(float) -0.1700764,(float) -0.013439285,(float) 0.16726658,(float) 0.36836216,(float) 0.58351094,(float) 0.8051464,(float) 1.0229396,(float) 1.2285992,(float) 1.4324409,(float) 1.6730129,(float) 1.9892191,(float) 2.3624659,(float) 2.6959639,(float) 2.8669596,(float) 2.7966132,(float) 2.477003,(float) 1.9541258,(float) 1.3045062,(float) 0.6307705,(float) 0.05442586,(float) -0.32863703,(float) -0.5063816,(float) -0.5508209,(float) -0.5508058,(float) -0.55125475,(float) -0.54923165,(float) -0.5316622,(float) -0.5056818,(float) -0.4905323,(float) -0.4927884,(float) -0.5026369,(float) -0.511617,(float) -0.5226538,(float) -0.5397273,(float) -0.557486,(float) -0.5681975,(float) -0.5737165,(float) -0.5825461,(float) -0.5962707,(float) -0.60664606,(float) -0.6077426,(float) -0.6040681,(float) -0.6027949,(float) -0.6036157,(float) -0.60227346, (float)-0.6007115,(float) -0.60546523,(float) -0.6151927,(float) -0.6173562,(float) -0.6018677, (float)-0.5740234,(float) -0.54852605,(float) -0.53334844,(float) -0.52554464,(float) -0.521515,(float) -0.52359456,(float) -0.5329663,(float) -0.5419728,(float) -0.5402035,(float) -0.5268787, (float)-0.51201147, (float)-0.5050308,(float) -0.5069488,(float) -0.5145626,(float) -0.5267139,(float) -0.542692,(float) -0.5577988,(float) -0.56623465,(float) -0.56824076,(float) -0.5693699,(float) -0.57191694,(float) -0.57105494,(float) -0.5614464,(float) -0.5438553,(float) -0.52165323,(float) -0.49445596,(float) -0.46071154,(float) -0.42623934,(float) -0.4034071,(float) -0.39805487,(float) -0.40123755, (float)-0.3976369,(float) -0.3812221,(float) -0.35917893,(float) -0.34149748,(float) -0.33199087,(float) -0.32950383,(float) -0.3319943,(float) -0.33497623,(float) -0.32861322,(float) -0.30119225,(float) -0.24604103,(float) -0.16432618,(float) -0.06356148,(float) 0.042029366,(float) 0.13077156, (float)0.18077044,(float) 0.18170333,(float) 0.13687417,(float) 0.052706983,(float) -0.06808441,(float) -0.21786228,(float) -0.37439826,(float) -0.51085794,(float) -0.61846566,(float) -0.71201676,(float) -0.8074793,(float) -0.90100205, (float)-0.97350466,(float) -1.0353855,(float) -1.0866444,(float) -1.1272817,(float) -1.157297};

    public double b [];

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

//        for (int i = 0; i < a.length; i++) {
//            Toast.makeText(getActivity(), String.valueOf(a.length[]), Toast.LENGTH_SHORT).show();
//        }
//        if (allc != null) {
//            Toast.makeText(getActivity(), String.valueOf(allc), Toast.LENGTH_SHORT).show();
//        }
//        if (allc == null) {
//            Toast.makeText(getActivity(), String.valueOf(allc), Toast.LENGTH_SHORT).show();
//        }

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
                mQueue.getCache().clear();
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

                        String isi = "3.8704317,3.481731,3.112639,2.7631557,2.4332812,2.1230156,1.8218051,1.5428181,1.2832037,1.0538046,0.85943544,0.68840075,0.5289379,0.38665625,0.27332777,0.18161932,0.08654734,-0.0206862,-0.108966105,-0.12719381,-0.0497196,0.101320304,0.27595317,0.42437986,0.5014028,0.46307,0.28456038,-0.010958054,-0.35087943,-0.6505429,-0.8579766,-0.966181,-0.99124056,-0.95187587,-0.86913055,-0.76952547,-0.67543626,-0.5922723,-0.5098972,-0.41574466,-0.3033343,-0.1700764,-0.013439285,0.16726658,0.36836216,0.58351094,0.8051464,1.0229396,1.2285992,1.4324409,1.6730129,1.9892191,2.3624659,2.6959639,2.8669596,2.7966132,2.477003,1.9541258,1.3045062,0.6307705,0.05442586,-0.32863703,-0.5063816,-0.5508209,-0.5508058,-0.55125475,-0.54923165,-0.5316622,-0.5056818,-0.4905323,-0.4927884,-0.5026369,-0.511617,-0.5226538,-0.5397273,-0.557486,-0.5681975,-0.5737165,-0.5825461,-0.5962707,-0.60664606,-0.6077426,-0.6040681,-0.6027949,-0.6036157,-0.60227346,-0.6007115,-0.60546523,-0.6151927,-0.6173562,-0.6018677,-0.5740234,-0.54852605,-0.53334844,-0.52554464,-0.521515,-0.52359456,-0.5329663,-0.5419728,-0.5402035,-0.5268787,-0.51201147,-0.5050308,-0.5069488,-0.5145626,-0.5267139,-0.542692,-0.5577988,-0.56623465,-0.56824076,-0.5693699,-0.57191694,-0.57105494,-0.5614464,-0.5438553,-0.52165323,-0.49445596,-0.46071154,-0.42623934,-0.4034071,-0.39805487,-0.40123755,-0.3976369,-0.3812221,-0.35917893,-0.34149748,-0.33199087,-0.32950383,-0.3319943,-0.33497623,-0.32861322,-0.30119225,-0.24604103,-0.16432618,-0.06356148,0.042029366,0.13077156,0.18077044,0.18170333,0.13687417,0.052706983,-0.06808441,-0.21786228,-0.37439826,-0.51085794,-0.61846566,-0.71201676,-0.8074793,-0.90100205,-0.97350466,-1.0353855,-1.0866444,-1.1272817,-1.157297";

                        String data = "{\"Reflectance\":[";
                        data += isi;
//                        for (int i = 0; i < b.length; i++){
//                            if (i != 0) // if it isn't the first one
//                                data += ", "; // insert a separator
//                            else
//                                data += a[i]; // append the index's toString
//                        }
                        data += "]}";
                        send(data);
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
                            JSONArray jsonArray2 = response.getJSONArray("wavenumber");

                            for (int i = 0; i < jsonArray.length(); i++) {


                                y = jsonArray.getDouble(i);
                                x = jsonArray2.getDouble(i);


//                                double x2[] = {0.2578602460655759,
//                                        0.3795816622876771,
//                                        0.3185359302556634,
//                                        0.181662000850082,
//                                        0.06461488819884664,
//                                        0.004866166724198706,
//                                        -0.005737917223058275,
//                                        0.004926377616399691,
//                                        0.019300967301589367,
//                                        0.06162229897007876,
//                                        0.12420153941187095,
//                                        0.1412723687195836,
//                                        0.08058154554898067,
//                                        -0.007230239208752209,
//                                        -0.02839118589858458,
//                                        0.05073724052800799,
//                                        0.14218422371537542,
//                                        0.12731831231857882,
//                                        0.001766171441246911,
//                                        -0.11649631846789532,
//                                        -0.11443427751395063,
//                                        -0.0045666011659903916,
//                                        0.09505189492568888,
//                                        0.09453548811035262,
//                                        0.01205663762196707,
//                                        -0.07939928944283281,
//                                        -0.14814185892387854,
//                                        -0.19491043931415675,
//                                        -0.19011894731112022,
//                                        -0.07659626538736575,
//                                        0.13938472354594467,
//                                        0.3101369760651522,
//                                        0.2813771192969625,
//                                        0.08796539261528835,
//                                        -0.08010494516700817,
//                                        -0.0632988518994324,
//                                        0.10631198863762847,
//                                        0.22364907134219436,
//                                        0.1542211368155222,
//                                        0.009877974647849896,
//                                        -0.005837734407521111,
//                                        0.1459957193917205,
//                                        0.29258779446820427,
//                                        0.2537551755490597,
//                                        0.06141923261905902,
//                                        -0.09051922205610197,
//                                        -0.07989246113628212,
//                                        -0.006377727858577487,
//                                        -0.06703747853707398,
//                                        -0.2801340547592446,
//                                        -0.4466801431984919,
//                                        -0.37694239576242694,
//                                        -0.11487813691113047,
//                                        0.11324988693392868,
//                                        0.1737801204073861,
//                                        0.14824501230495457,
//                                        0.19182470643336558,
//                                        0.325923092413106,
//                                        0.42115888425556136,
//                                        0.36658417633130114,
//                                        0.1916110383750862,
//                                        0.011062625982987129,
//                                        -0.13036993736606917,
//                                        -0.2723032564338297,
//                                        -0.4002494881784884,
//                                        -0.3887884582864416,
//                                        -0.1666087348295946,
//                                        0.14014578218959173,
//                                        0.3444144075222937,
//                                        0.35097509004611993,
//                                        0.1973146080748336,
//                                        0.023051389731548966,
//                                        -0.07163338012367149,
//                                        -0.10414587082570392,
//                                        -0.10712164707311445,
//                                        -0.06663661949971811,
//                                        0.024561918676027972,
//                                        0.1254733739747138,
//                                        0.1772725457095703,
//                                        0.15305141857348303,
//                                        0.07791381487244564,
//                                        0.0053523309245804285,
//                                        -0.01387240470764084,
//                                        0.03941736159865172,
//                                        0.11649284554165718,
//                                        0.13374157785007412,
//                                        0.0387189521291873,
//                                        -0.09995719281334914,
//                                        -0.09602886173770742,
//                                        0.0450743560037381,
//                                        0.1228471731684806,
//                                        0.003571492985983582,
//                                        -0.2928755473290323,
//                                        -0.4626528559213057,
//                                        -0.2634260251400349,
//                                        0.047883181295617305,
//                                        0.1639020377054834,
//                                        0.013629973109644311,
//                                        -0.1497508455299652,
//                                        -0.05629349241793591,
//                                        0.14740099157219788,
//                                        0.20643907754018187,
//                                        0.0737076297246233,
//                                        -0.015155034636521236,
//                                        0.14949195724609865,
//                                        0.4086667352378299,
//                                        0.4909538642539104,
//                                        0.30604718481053794,
//                                        0.045990272854709247,
//                                        -0.03617666858701796,
//                                        0.059986235765734364,
//                                        0.21229116515002033,
//                                        0.3625255894919377,
//                                        0.43165462777415087,
//                                        0.34082846206889883,
//                                        0.11102249347628401,
//                                        -0.10678814574569628,
//                                        -0.12824945101479557,
//                                        -0.08842832131627176,
//                                        -0.22130974036966222,
//                                        -0.5314671651346998,
//                                        -0.7025043051454816,
//                                        -0.3785928581790756,
//                                        0.23977597717397714,
//                                        0.6261698858576636,
//                                        0.478164740828376,
//                                        0.13666862623533405,
//                                        0.14672110100850944,
//                                        0.49023238314811124,
//                                        0.7139052984121719,
//                                        0.4090953510080766,
//                                        -0.14782847700743673,
//                                        -0.2644978777984903,
//                                        0.1892858301706184,
//                                        0.7484685843336365,
//                                        0.7273472630030398,
//                                        0.11764786187517018,
//                                        -0.2905944695865088,
//                                        -0.20569765459475775,
//                                        0.0772323889482891,
//                                        0.06233687868899551,
//                                        -0.4555828649738629,
//                                        -1.0867707951694854,
//                                        -1.2640455039221195,
//                                        -0.8249530193177748,
//                                        -0.1751322601224814,
//                                        0.15827516441237321,
//                                        -0.023355481726326843,
//                                        -0.4566900967954268,
//                                        -0.5542832711130927,
//                                        -0.16618880854073836,
//                                        0.07893393352934197,
//                                        -0.25555592638514213,
//                                        -0.9548396812310358,
//                                        -1.3610418172040113,
//                                        -0.8749632031634036,
//                                        0.2762905010014265,
//                                        1.049193564594887,
//                                        0.4267276187902098,
//                                        -0.7215983697757338,
//                                        -0.843618051237371,
//                                        -0.0609059942831891,
//                                        1.1746725418619377,
//                                        2.2339207569638546,
//                                        2.0299310065834817,
//                                        0.9917793320702231,
//                                        0.500847471642814,
//                                        0.7915958382745742,
//                                        0.8794697112341368,
//                                        0.36952624771049614,
//                                        -0.45677914314934753,
//                                        -1.306667044727476,
//                                        -1.4036131898200779,
//                                        -0.11481535581162916,
//                                        2.1960178267602544};
                                int a = jsonArray.length();
                                float x2[] = new float[a+1];
                                x2[i] = (float) y;

                                String array = Arrays.toString(x2);
                                Toast.makeText(getActivity(), array , Toast.LENGTH_LONG).show();



                                b = new double[i+1];
                                b [i]= y;

                                tampung.append(String.valueOf(b[i]));

                                Log.d("absorbance :", String.valueOf(y));
                                Log.d("absorbance :", String.valueOf(b[i]));
//
//                                Log.d("wavenumber :", String.valueOf(x));

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

    public void send( String data){
        Wave rotatingCircle = new Wave();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminateDrawable(rotatingCircle);
        lUtama.setVisibility(View.GONE);
        lLoading.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final String savedata = data;
        String URL = "https://sskapi.azurewebsites.net/api/Inference/ProcessData";
        mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getActivity(), "RESPONSE:" + jsonObject, Toast.LENGTH_LONG).show();
                            Log.e("response:" , response);
                            lUtama.setVisibility(View.VISIBLE);
                            lLoading.setVisibility(View.GONE);
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                }catch (UnsupportedEncodingException uee){
                    return null;
                }
            }
        };
        mQueue.add(stringRequest);

    }
}