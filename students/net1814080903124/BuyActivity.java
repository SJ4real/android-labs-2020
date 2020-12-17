package edu.hzuapps.androidlabs.net1814080903124;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

public class BuyActivity extends AppCompatActivity {
    Integer position;
    String type;
    private static final String TAG = "BuyActivity";
    CityPickerView mPicker=new CityPickerView();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        mPicker.init(this);
        Intent mIn = getIntent();
        type = mIn.getStringExtra("type");
        position = mIn.getIntExtra("id",0);

        Button button;
        button=findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        TextView textView;
        textView=findViewById(R.id.comsumer_address_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddress();
            }});

        ImageView imageView;
        imageView=findViewById(R.id.imageView_buy);
        if(type.equals("sneaker"))
        {
        Glide.with(this)
                .load(FindImage.Sneaker_url[position])
                .apply(new RequestOptions()
                        .dontAnimate())
                .into(imageView);
        }
        else if(type.equals("clothes"))
        {
            Glide.with(this)
                    .load(FindImage.Clothes_url[position])
                    .apply(new RequestOptions()
                            .dontAnimate())
                    .into(imageView);
        }
        else
        {
            Glide.with(this)
                    .load(FindImage.Acc_url[position])
                    .apply(new RequestOptions()
                            .dontAnimate())
                    .into(imageView);
        }
    }

    private void selectAddress(){
        //添加默认的配置，不需要自己定义
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);
        final TextView textView;
        textView=findViewById(R.id.comsumer_address_text);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //将选择的地址填入tv_address_set中
                textView.setText(province.toString().trim() + city.toString().trim() + district.toString().trim());
            }
            @Override
            public void onCancel() {
                ToastUtils.showLongToast(BuyActivity.this, "已取消");
            }
        });
        //显示
        mPicker.showCityPicker( );
    }

    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        EditText editText;
        editText=findViewById(R.id.comsumer_name_text);
        Editable name;
        name=editText.getText();
        builder.setTitle("温馨提示");
        builder.setMessage(name+",恭喜您下单成功！");
        AlertDialog dialog=builder.create();
        dialog.show();

    }
}