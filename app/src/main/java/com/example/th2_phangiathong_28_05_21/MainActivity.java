package com.example.th2_phangiathong_28_05_21;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText txtmasv, txttensv;
    Button btnthem, btnsua, btnluu, btnlamlai, btnxoa;
    Spinner spinnerlop;
    ListView lvsv;
    RadioGroup radiogroupsex;
    RadioButton radiobuttonsex;
    ArrayList<String> arrlistLop;
    //Đưa dữ liệu lên list view.
    ArrayList<String> arraylistsv;
    ArrayAdapter adaptersv=null;
    int thaotac = 0; //Mặc định là thao tác thêm mới.
    String lop=""; //Spinner
    int vt = -1; //Chưa chọn vị trí nào cả trong list view.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtmasv = findViewById(R.id.txtMaSv);
        txttensv = findViewById(R.id.txtTenSv);
        spinnerlop = findViewById(R.id.spinnerLop);
        //b1 Khởi tạo dữ liệu cho spinder
        arrlistLop = new ArrayList<String>();
        arrlistLop.add("Khóa 59");
        arrlistLop.add("Khóa 60");
        arrlistLop.add("Khóa 61");
        arrlistLop.add("Khóa 62");
        //b2 Adapter
        ArrayAdapter<String> adapterlop=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrlistLop);
        adapterlop.setDropDownViewResource(android.R.layout.simple_spinner_item); //Xổ xuống
        spinnerlop.setAdapter(adapterlop); //set adapter vào
        //Bắt sự kiên cho spinner
        spinnerlop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override //Lấy giá trị trong spinner ra
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lop=arrlistLop.get(position).toString();
            }

            @Override //Không lấy gì cả
            public void onNothingSelected(AdapterView<?> parent) {
                lop="";
            }
        });

        lvsv = findViewById(R.id.listviewSv);
        arraylistsv = new ArrayList<String>(); //Biến arraylistsv rỗng
        //arraylistsv.add("01-Thong-1998"); //Thêm vào listview
        adaptersv=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraylistsv);
        lvsv.setAdapter(adaptersv);
        //Sự kiện click của click view
        lvsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vt=position;
                String str = arraylistsv.get(position).toString();
                String a[]=str.split("-");
                //str =01-Thong-Nam-K60
                //a[0]->01, a[1]->Thong, a[2]->Nam ...
                txtmasv.setText(a[0]);
                txttensv.setText(a[1]);
                //Hiện thị lại giới tính khi nhấn
                if(a[2].equalsIgnoreCase("Nam")) {
                    radiobuttonsex=findViewById(R.id.rdoBtnNam);
                    radiogroupsex.check(radiobuttonsex.getId());
                }else{
                    radiobuttonsex=findViewById(R.id.rdoBtnNu);
                    radiogroupsex.check(radiobuttonsex.getId());
                }

                //Hiện thị list view khi nhấn
                selectValue(spinnerlop,a[3]);
                //Sửa dữ liệu
                btnthem.setEnabled(false);
                btnsua.setEnabled(true);
                btnluu.setEnabled(false);
            }
        });
        //Sự kiện longclick listview
        lvsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Hàm nào đó để show thông báo có chắc xóa không.
                //position là vị trí mà mình chọn trên list view
                showD(position);
                return false;
            }
        });
        radiogroupsex = findViewById(R.id.rdoGroupSex);
        btnthem = findViewById(R.id.btnThem);
        btnsua = findViewById(R.id.btnSua);
        //Bắt sụw kiện nút sửa.
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnthem.setEnabled(false);
                btnsua.setEnabled(false);
                btnluu.setEnabled(true);
                thaotac=1; //Người dùng đang nhấn thao tác sửa.
            }
        });
        btnlamlai = findViewById(R.id.btnLamlai);
        btnluu =findViewById(R.id.btnLuu);
        btnxoa =findViewById(R.id.btnxoa);
        //Bắt sự kiện nút xóa
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hàm nào đó để show thông báo có chắc xóa không.
                showD(vt);
            }
        });
        //
        btnthem.setEnabled(true); //Làm cho nút sáng lên.
        btnsua.setEnabled(false);
        btnluu.setEnabled(false);
        //Bắt sự kiện
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thaotac==0) { //Người dùng đang thêm mới.
                    if(txtmasv.getText().toString().equalsIgnoreCase("")){
                        txtmasv.setError("Mã không được để trống !");
                    }else {
                    //Lấy dữ liệu người dùng nhập
                    String masv = txtmasv.getText().toString();
                    String tensv = txttensv.getText().toString();
                    //Lấy giới tính
                    int selectedid = radiogroupsex.getCheckedRadioButtonId();
                    radiobuttonsex = findViewById(selectedid);
                    String full = masv + "-" + tensv + "-" + radiobuttonsex.getText().toString() +"-"+lop;
                    arraylistsv.add(full);
                    adaptersv.notifyDataSetChanged(); //Báo listview nguồn dữ liệu có thay đổi và tự động update.
                    btnthem.setEnabled(true);
                    btnsua.setEnabled(false);
                    btnluu.setEnabled(false);
                    resetView();
                    }
                }else { //Sửa thông tin, nhấn vào nút sửa
                    arraylistsv.remove(vt);
                    //Lấy dữ liệu người dùng nhập
                    String masv = txtmasv.getText().toString();
                    String tensv = txttensv.getText().toString();
                    //Lấy giới tính
                    int selectedid = radiogroupsex.getCheckedRadioButtonId();
                    radiobuttonsex = findViewById(selectedid);
                    String full = masv + "-" + tensv + "-" + radiobuttonsex.getText().toString() +"-"+lop;
                    arraylistsv.add(full);
                    adaptersv.notifyDataSetChanged(); //Báo listview nguồn dữ liệu có thay đổi và tự động update.
                    btnthem.setEnabled(true);
                    btnsua.setEnabled(false);
                    btnluu.setEnabled(false);
                    resetView();
                }
            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thaotac = 0;
                btnthem.setEnabled(false);
                btnsua.setEnabled(false);
                btnluu.setEnabled(true);
                resetView();
            }
        });
        btnlamlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnthem.setEnabled(true); //Làm cho nút sáng lên.
                btnsua.setEnabled(false);
                btnluu.setEnabled(false);
                resetView();
            }
        });
    }
    //Clear giá trị trên edittext
    private void resetView() {
        thaotac = 0;
        txtmasv.requestFocus();
        txtmasv.setText("");
        txttensv.setText("");
        //Button radio trở lại ban đầu
        radiobuttonsex=findViewById(R.id.rdoBtnNam);
        radiogroupsex.check(radiobuttonsex.getId());
        //Spinner trở lại ban đầu.
        spinnerlop=findViewById(R.id.spinnerLop);
        spinnerlop.setSelection(0);
    }
    //Hiện thị lại giá trị của list view khi nhấn
    private void selectValue(Spinner spinner, Object value) {
        for(int i=0;i<spinner.getCount();i++) {
            if(spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    //Hàm show Dialog của nút xóa và listview
    private void showD(int i) {
        //partter buider Phương thức giúp theo giõi những sự thay đổi.
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Are you sure to delete this item ?");
        //Yes
        builder.setPositiveButton("Yes",(dialog, which) -> {
            //Remove 1 phần tử trong mảng array list
            arraylistsv.remove(i); //Xóa 1 phần tử trong mảng
            adaptersv.notifyDataSetChanged(); //Update dữ liệu list view
            dialog.dismiss();//Đóng cửa sổ thông báo.

            vt=-1; //Sau khi xóa thì trả lại trạng thái ban đầu.

            resetView();
        });
        //No
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//Đóng cửa sổ thông báo.
            }
        });
        //Show
        AlertDialog alert = builder.create();
        alert.show();
    }
}